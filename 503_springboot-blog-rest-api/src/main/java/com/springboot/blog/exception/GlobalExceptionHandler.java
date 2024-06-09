package com.springboot.blog.exception;

import com.springboot.blog.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorDetailsDto errorDetailsDto =
                new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
    }

}
