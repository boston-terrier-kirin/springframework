package com.jrp.pma.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entity.UserAccount;

/**
 * AuthenticationProviderを自前で実装する場合
 * デバッグする際に使える。
 */
@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private UserAccountRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		UserAccount customer = customerRepository.findByUsername(username);

		if (passwordEncoder.matches(pwd, customer.getPassword())) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
			return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
		} else {
			throw new BadCredentialsException("Invalid password!");
		}
	}
	
	@Override
	public boolean supports(Class<?> authenticationType) {
		return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
	}
}
