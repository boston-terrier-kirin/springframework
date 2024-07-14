package com.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                        authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
                                              .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                                              .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAnyRole("ADMIN", "GUEST")
                                              .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                              .requestMatchers(new AntPathRequestMatcher("/post/**")).permitAll()
                                              .anyRequest().authenticated()
                    );

        httpSecurity.formLogin(form -> form.loginPage("/login")
                                            .usernameParameter("username")
                                            .passwordParameter("password")
                                            .loginProcessingUrl("/login")
                                            .defaultSuccessUrl("/admin/posts", true)
                                            .permitAll()
                    );

        httpSecurity.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                    );

        return httpSecurity.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder);
    }
}
