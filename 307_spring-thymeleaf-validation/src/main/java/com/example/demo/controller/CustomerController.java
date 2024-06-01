package com.example.demo.controller;

import com.example.demo.model.Customer;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController extends BaseController {

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer-form";
    }

    @PostMapping("/processForm")
    public String processForm(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {

        System.out.println(customer);

        // bindingResultのデバッグ方法
        System.out.println(bindingResult.toString());
        System.out.println("=====");
        bindingResult.getAllErrors().stream().forEach(err -> System.out.println(err));
        System.out.println("=====");

        if (bindingResult.hasErrors()) {
            return "customer-form";
        }

        return "customer-confirm";
    }
}
