package com.example.demo.controller;

import com.example.demo.model.Grade;
import com.example.demo.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * https://www.learnthepart.com/course/af54547f-e993-47bd-ad51-d7c7270c4e50/21beafb0-3781-4b5e-967c-4eb0ebae13b8
 */
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/grades")
    public String getGrades(Model model) {
        List<Grade> grades = this.gradeService.getGrades();
        model.addAttribute("grades", grades);
        return "grades";
    }

    @GetMapping("/grades/new")
    public String newForm(Model model) {
        model.addAttribute("grade", new Grade());
        return "form";
    }

    @GetMapping("/grades/update")
    public String updateForm(Model model, @RequestParam(required = true) String id) {
        var grade = this.gradeService.getGradeById(id);

        model.addAttribute("grade", grade);
        return "form";
    }

    @PostMapping("/grades/save")
    public String save(@Valid Grade grade, BindingResult bindingResult) {
        if (grade.getName().equals("John Doe")) {
            bindingResult.rejectValue("name", "", "Name must not be John Doe");
        }

        if (bindingResult.hasErrors()) {
            return "form";
        }

        this.gradeService.submitGrade(grade);

        return "redirect:/grades";
    }
}
