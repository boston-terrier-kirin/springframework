package com.example.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showLogin")
	public String showLogin() {
		return "custome-login";
	}

	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "access-denied";
	}
}
