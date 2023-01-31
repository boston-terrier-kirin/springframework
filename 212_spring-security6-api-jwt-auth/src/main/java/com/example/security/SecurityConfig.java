package com.example.security;


import com.example.security.filter.AuthenticationFilter;
import com.example.security.filter.ExceptionHandlerFilter;
import com.example.security.filter.JWTAuthorizationFilter;
import com.example.security.manager.CustomAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * AuthenticationFilterの親であるUsernamePasswordAuthenticationFilterが、/loginをデフォルトでセットしているので、
         * /authenticateに変える。
         */
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");

        // h2がframeを使っている。
        http.headers().frameOptions().disable();

        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers("/test").permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /**
         * h2を認証なしにする方法。toH2Console()を使うと上手くいく。
         * ”/h2-console” では上手くいかなかった。
         * https://stackoverflow.com/questions/74680244/h2-database-console-not-opening-with-spring-security
         */

        return http.build();
    }
}