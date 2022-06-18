package com.example.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(value = { UserServiceException.class })
	public ResponseEntity<ErrorMessage> handleUserServiceException(UserServiceException ex, WebRequest request) {
		logger.error("★handleUserServiceException");
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorMessage> handleAnyExceptions(Exception ex, WebRequest request) {
		logger.error("★handleAnyExceptions");
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
