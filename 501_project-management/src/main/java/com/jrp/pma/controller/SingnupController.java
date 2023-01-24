package com.jrp.pma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jrp.pma.entity.UserAccount;
import com.jrp.pma.service.UserAccountService;

/**
 * https://www.baeldung.com/spring-security-login-error-handling-localization
 * https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
 * https://www.baeldung.com/registration-verify-user-by-email
 * 
 * https://www.javadevjournal.com/spring/what-is-spring-security/
 * https://github.com/javadevjournal/javadevjournal/tree/master/spring-security/spring-security-series
 * 
 * https://fueiseix.hatenablog.com/entry/2018/03/11/191200
 */
@Controller
public class SingnupController {
	
	@Autowired
	private UserAccountService userAccountService;

	@GetMapping("/signup")
	public String showSignup(Model model) {
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount", userAccount);
		
		return "/signup/signup";
	}

	@PostMapping("/signup/save")
	public String signup(UserAccount userAccount, Model model) {
		
		String username = userAccount.getUsername();
		String password = userAccount.getPassword();
		
		this.userAccountService.save(userAccount);
		this.userAccountService.authenticate(username, password);

		return "redirect:/employees";
	}
}
