package com.springboot.blog.controller;

import com.springboot.blog.dto.RegistrationDto;
import com.springboot.blog.entity.User;
import com.springboot.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class AuthController {

    private final MessageSource messageSource;
    private final UserService userService;

    @Autowired
    public AuthController(MessageSource messageSource, UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "blog/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("userDto", new RegistrationDto());

        return "blog/register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("userDto") @Valid RegistrationDto registrationDto,
                           BindingResult bindingResult,
                           Model model) {

        User existingUser = this.userService.findByEmail(registrationDto.getEmail());
        if (existingUser != null) {
            String message = this.messageSource.getMessage("errors.email.already.taken", null, Locale.JAPAN);
            bindingResult.rejectValue("email", null, message);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", registrationDto);
            return "blog/register";
        }

        this.userService.saveUser(registrationDto);

        return "redirect:/register?success";
    }
}
