package com.jrp.pma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jrp.pma.entity.UserAccount;

@Controller
public class SigninController {

	@GetMapping("/signin")
	public String showSignin(Model model) {
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount", userAccount);
		
		return "/signin/signin";
	}
}
