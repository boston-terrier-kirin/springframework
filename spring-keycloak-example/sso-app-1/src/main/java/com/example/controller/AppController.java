package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value = {"/contact-us"})
	public ModelAndView contactUs() {
		logger.info("💨AppController.contactUs");

		ModelAndView modelAndView = new ModelAndView("contact-us");
		return modelAndView;
	}

	@GetMapping(value = {"/home"})
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("home");
		return modelAndView;
	}

	@GetMapping(value = {"/manager"})
	public ModelAndView manager() {
		ModelAndView modelAndView = new ModelAndView("manager");
		return modelAndView;
	}

	@GetMapping(value = {"/access-denied"})
	public ModelAndView AccessDenied() {
		ModelAndView modelAndView = new ModelAndView("access-denied");
		return modelAndView;
	}
}
