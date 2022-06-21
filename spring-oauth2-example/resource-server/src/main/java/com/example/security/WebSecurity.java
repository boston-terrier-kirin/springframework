package com.example.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * EnableGlobalMethodSecurityは、コントローラーで@Secured、@PostAuthorizeを使うために必要。
 */
@SuppressWarnings("deprecation")
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

		http.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET, "/users/status/check")
				// .hasAuthority("SCOPE_profile")
				.hasRole("developer")
			.anyRequest().authenticated();
		
		http.oauth2ResourceServer()
			.jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
