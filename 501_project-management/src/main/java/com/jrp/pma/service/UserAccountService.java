package com.jrp.pma.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entity.UserAccount;
import com.jrp.pma.entity.UserSecurity;

@Service
public class UserAccountService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	public UserAccount save(UserAccount userAccount) {
		logger.info("★save");

		userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
		UserAccount savedUser = this.userAccountRepository.save(userAccount);
		return savedUser;
	}
	
	public void authenticate(String username, String password) {
		logger.info("★authenticate");
		
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("★loadUserByUsername");

		UserAccount userAccount = this.userAccountRepository.findByUsername(username);
		if (userAccount == null) {
			throw new UsernameNotFoundException("User details not found for: " + username);
		}

		return new UserSecurity(userAccount);
	}
}
