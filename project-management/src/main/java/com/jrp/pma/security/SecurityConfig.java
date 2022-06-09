package com.jrp.pma.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jrp.pma.service.UserAccountService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserAccountService userAccountService;

// authenticationProviderを独自で実装する場合
//	@Autowired
//	private AuthProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// このやりかたもあり
//		auth.jdbcAuthentication().dataSource(ds)
//			.usersByUsernameQuery("select username, password, enabled from user_accounts where username = ?")
//			.authoritiesByUsernameQuery("select username, role from user_accounts where username = ?")
//			.passwordEncoder(this.passwordEncoder);

// デフォルトのスキーマを使う場合
//		auth.jdbcAuthentication().dataSource(ds)
//			.withDefaultSchema()
//			.withUser("john").password("11111").roles("USER")
//			.and()
//			.withUser("admin").password("11111").roles("ADMIN");

// インメモリを使う場合
//		auth.inMemoryAuthentication().withUser("john").password("11111").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("11111").roles("ADMIN");

// authenticationProviderを独自で実装する場合
//		auth.authenticationProvider(this.authProvider);

		auth.userDetailsService(this.userAccountService).passwordEncoder(this.passwordEncoder);
	}
	
	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.mvcMatchers("/css/**").permitAll()
			.mvcMatchers("/js/**").permitAll()
			.mvcMatchers("/signup/**").permitAll()
			.anyRequest().authenticated();

		http.formLogin()
			.loginPage("/signin")
			.loginProcessingUrl("/signin/authenticateUser")
			.successForwardUrl("/employees")
			.defaultSuccessUrl("/employees")
			.failureUrl("/signin")
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll();
	
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/signin")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.permitAll();

		http.sessionManagement()
			.invalidSessionUrl("/invalidSession");

		// h2-consoleにログインするためにはこの設定が必要らしい。
		// http.csrf().disable();
		// http.headers().frameOptions().disable();
	}
}
