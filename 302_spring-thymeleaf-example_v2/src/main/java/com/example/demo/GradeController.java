package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GradeController {

    List<Grade> grades = new ArrayList<>();

    @GetMapping("/grades")
    public String getGrades(Model model) {
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
        var grade = grades.get(getGradeIndex(id));

        model.addAttribute("grade", grade);
        return "form";
    }

    @PostMapping("/grades/save")
    public String save(Grade grade, Model model) {
        var idx = getGradeIndex(grade.getId());
        if (idx == Constants.NOT_FOUND) {
            grades.add(grade);
        } else {
            grades.set(idx, grade);
        }

        return "redirect:/grades";
    }

    private int getGradeIndex(String id) {
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
}
