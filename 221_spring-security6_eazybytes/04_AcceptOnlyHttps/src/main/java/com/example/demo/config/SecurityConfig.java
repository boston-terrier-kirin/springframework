package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests ->
                                        requests.requestMatchers("/myAccount", "/myBalance", "myLoans", "myCards").authenticated()
                                                .requestMatchers("/register", "/contact", "/notices", "/error").permitAll());

        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.csrf(csrfConfig -> csrfConfig.disable());

        // httpsのみ許可する。
        // http.requiresChannel(channel -> channel.anyRequest().requiresSecure());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
