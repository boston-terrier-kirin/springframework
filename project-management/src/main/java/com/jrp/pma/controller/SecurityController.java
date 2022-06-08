package com.jrp.pma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jrp.pma.entity.UserAccount;
import com.jrp.pma.service.UserAccountService;

@Controller
public class SecurityController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserAccountService userAccountService;

	@GetMapping("/register")
	public String register(Model model) {
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount", userAccount);
		
		return "/security/register";
	}

	@PostMapping("/register/save")
	public String saveUser(UserAccount userAccount, Model model) {
		userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
		this.userAccountService.save(userAccount);

		return "redirect:/";
	}
}
