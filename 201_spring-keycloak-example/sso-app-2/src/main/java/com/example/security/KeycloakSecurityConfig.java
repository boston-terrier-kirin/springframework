package com.example.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGloabl(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(keycloakAuthenticationProvider());
	}
	
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);

		http.authorizeRequests()
			.antMatchers("/contact-us").permitAll()
			.antMatchers("/manager/**").hasAuthority("Manager")
			.anyRequest().authenticated();

		http.exceptionHandling().accessDeniedPage("/access-denied");
	}
}

