package com.jrp.pma.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppErrorController implements ErrorController {

	@GetMapping("/error")
	public String handleError(HttpServletRequest req) {
		Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "/error/error-403";
			}
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "/error/error-404";
			}
			if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "/error/error-500";
			}
		}

		return "/error/error";
	}

// これはなくてもOK
//	public String getErrorPath() {
//		return "/error";
//	}
}
