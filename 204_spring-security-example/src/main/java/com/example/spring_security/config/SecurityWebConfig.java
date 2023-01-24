package com.example.spring_security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 5.7でセキュリティ設定の書き方が大幅に変わる件
 * https://qiita.com/suke_masa/items/908805dd45df08ba28d8
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource ds;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.ds);

//		UserBuilder users = User.withDefaultPasswordEncoder();
//		auth.inMemoryAuthentication()
//			.withUser(users.username("john").password("11111").roles("EMPLOYEE"))
//			.withUser(users.username("mary").password("11111").roles("EMPLOYEE", "MANAGER"))
//			.withUser(users.username("susan").password("11111").roles("EMPLOYEE", "ADMIN"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/").hasRole("EMPLOYEE")
				.antMatchers("/leaders/**").hasRole("MANAGER")
				.antMatchers("/systems/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/showLogin")
				.loginProcessingUrl("/authenticateUser")
				.permitAll()
			.and()
				.logout()
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
}
