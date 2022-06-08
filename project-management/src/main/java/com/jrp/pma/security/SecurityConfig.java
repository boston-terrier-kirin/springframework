package com.jrp.pma.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource ds;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(ds)
			.usersByUsernameQuery("select username, password, enabled from user_accounts where username = ?")
			.authoritiesByUsernameQuery("select username, role from user_accounts where username = ?")
			.passwordEncoder(this.passwordEncoder);

// デフォルトのスキーマを使う場合
//		auth.jdbcAuthentication().dataSource(ds)
//			.withDefaultSchema()
//			.withUser("john").password("11111").roles("USER")
//			.and()
//			.withUser("admin").password("11111").roles("ADMIN");

// インメモリを使う場合
//		auth.inMemoryAuthentication().withUser("john").password("11111").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("11111").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.mvcMatchers("/projects/new").hasRole("ADMIN")
			.mvcMatchers("/projects/save").hasRole("ADMIN")
			.mvcMatchers("/employees/new").hasRole("ADMIN")
			.mvcMatchers("/employees/save").hasRole("ADMIN")
			.mvcMatchers("/h2-console").permitAll()
			.mvcMatchers("/", "/**").permitAll();

		http.formLogin();

		// h2-consoleにログインするためにはこの設定が必要らしい。
		// http.csrf().disable();
		// http.headers().frameOptions().disable();
	}
}
