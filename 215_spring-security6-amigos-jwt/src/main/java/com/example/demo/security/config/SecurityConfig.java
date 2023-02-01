package com.example.demo.security.config;

import com.example.demo.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // h2がframeを使っている。
        http.headers().frameOptions().disable();

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authenticationProvider(authenticationProvider);

        /**
         * ・UsernamePasswordAuthenticationFilterをextendsする。
         *     OR
         * ・UsernamePasswordAuthenticationFilterより前でauthenticateする。
         */

        /**
         * UsernamePasswordAuthenticationFilterは/loginなど、決まったURLでしか動かない。
         * JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter でjwtを発行してしまうと、
         * /loginのタイミングでしか発行できない。
         *
         * /registerでも/loginでも発行したい場合は、
         * ・JwtAuthenticationFilter extends OncePerRequestFilter でjwtのチェックのみ
         * ・/login /register のサービスでjwtを発行した方が自由度が上がる。
         */
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
