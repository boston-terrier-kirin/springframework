package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * https://qiita.com/suke_masa/items/908805dd45df08ba28d8
 * WebSecurityConfigurerAdapter はおそらくなくなった。
 */
@Configuration
public class SecurityConfig {

    /**
     * IntelliJ Shift + Shift で検索。
     * SpringBootWebSecurityConfiguration。
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var kirin = User.withUsername("kirin").password("{noop}test").roles("ADMIN").build();
        var kuroro = User.withUsername("kuroro").password("{noop}test").roles("USER").build();

        return new InMemoryUserDetailsManager(kirin, kuroro);
    }
}
