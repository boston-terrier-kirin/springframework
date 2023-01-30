package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConditionalsController {

    @GetMapping(value="/conditionals")
    public String getMethodName(Model model) {
        model.addAttribute("sales", 49);
        return "conditionals";
    }
}
