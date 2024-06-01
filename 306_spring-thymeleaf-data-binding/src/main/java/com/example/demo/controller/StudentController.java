package com.example.demo.controller;

import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Value("${countries}")
    private List<String> countries;

    @Value("${programming.languages}")
    private List<String> programmingLanguages;

    @Value("${operating.systems}")
    private List<String> operatingSystems;

    @GetMapping("/showStudentForm")
    public String showForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        model.addAttribute("countries", this.countries);
        model.addAttribute("programmingLanguages", this.programmingLanguages);
        model.addAttribute("operatingSystems", this.operatingSystems);

        return "student-form";
    }

    @PostMapping("processStudentForm")
    public String processForm(@ModelAttribute("student") Student student, Model model) {
        // どっちでもOK
        System.out.println("@ModelAttribute: " + student);
        System.out.println("Model: " + model.getAttribute("student"));

        return "student-confirm";
    }
}
