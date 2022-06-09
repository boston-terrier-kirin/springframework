package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ★忘れがち
        // csrfが効いているとPostmanでPOSTした時に403になってしまう。
        // SpringSecurityがdebugでログを出しているので、なかなか気づくのが大変。
        http.csrf().disable();

        http.authorizeHttpRequests()
                .mvcMatchers(HttpMethod.POST, "/signup").permitAll()
                .anyRequest().authenticated();

        // テスト向けにデフォルトのログインページを表示。
        http.formLogin();
    }

    /**
     * https://bcrypt-generator.com/
     * roundは12が正しい。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

// InMemoryUserDetailsManager
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /**
//         * InMemoryUserDetailsManagerもUserDetailsServiceを実装している。
//         */
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//
//        /**
//         * UserはUserDetailsをデフォルト実装。
//         * 自分でUserDetailsを実装することもあるが、Userを使ってもOK。
//         */
//        UserDetails user1 = User.withUsername("john").password("11111").authorities("admin").build();
//        UserDetails user2 = User.withUsername("jane").password("11111").authorities("admin").build();
//
//        userDetailsService.createUser(user1);
//        userDetailsService.createUser(user2);
//
//        auth.userDetailsService(userDetailsService);
//    }

// JdbcUserDetailsManager
//    /**
//     * UserDetailsServiceを公開すればSpringが勝手に見つけてくれる。
//     * dsはインジェクトされる。
//     */
//    @Bean
//    public UserDetailsService userDetailsService(DataSource ds) {
//        return new JdbcUserDetailsManager(ds);
//    }
}
