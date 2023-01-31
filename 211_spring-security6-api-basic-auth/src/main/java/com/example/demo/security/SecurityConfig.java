package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * In Spring Security 5.7.0-M2 we deprecated the WebSecurityConfigurerAdapter.
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * https://qiita.com/suke_masa/items/908805dd45df08ba28d8
         * antMatchers() や mvcMatchers() ではなく requestMatchers() を使いましょう
         */

        /**
         * DELETE：ADMINのみ
         * POST：ADMIN / USER
         * GET:誰でもOK
         */
        http.csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.GET, "/contacts", "/contact/*").permitAll()
                .anyRequest().authenticated();
        http.httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("test"))
                        .roles("ADMIN").build();
        var user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("test"))
                .roles("USER").build();
        var guest = User.builder()
                .username("guest")
                .password(passwordEncoder.encode("test"))
                .roles("GUEST").build();

        return new InMemoryUserDetailsManager(admin, user, guest);
    }

     @Bean
     public PasswordEncoder passwordEncoder() {
     	return new BCryptPasswordEncoder();
     }
}
