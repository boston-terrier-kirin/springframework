package com.jrp.pma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entity.UserAccount;

@Service
public class UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	public UserAccount save(UserAccount userAccount) {
		return this.userAccountRepository.save(userAccount);
	}
}
