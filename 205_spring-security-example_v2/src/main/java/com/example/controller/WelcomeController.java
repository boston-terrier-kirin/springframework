package com.example.controller;

import com.example.model.SignupRequest;
import com.example.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @PostMapping("/signup")
    public String createUser(@RequestBody SignupRequest signupRequest) {

       this.customerUserDetailsService.createUser(signupRequest);

        return "User Created";
    }

    @PostMapping("/hello")
    public String hello() {
        return "hello";
    }

}
