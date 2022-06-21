package com.appsdeveloerblog.social.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String displayHome(Model model, @AuthenticationPrincipal OAuth2User principal) {

		if (principal != null) {
			String name = principal.getAttribute("name");
			model.addAttribute("name", name);
		}

		return "home";
	}
}
