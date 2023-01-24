package com.example.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

		http.cors();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

/**
 * 課題
 * The 'Access-Control-Allow-Origin' header contains multiple values '*, *', but only one is allowed.
 * になってしまう。これはヘッダーの設定が二重になっていることが原因っぽい。
 */
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("*"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//		
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		
//		return source;
//	}
}
