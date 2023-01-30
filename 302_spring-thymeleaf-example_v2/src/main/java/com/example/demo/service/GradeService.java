package com.example.demo.service;

import com.example.demo.Constants;
import com.example.demo.model.Grade;
import com.example.demo.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getGrades() {
        return this.gradeRepository.getGrades();
    }

    public Grade getGrade(int idx) {
        return this.gradeRepository.getGrade(idx);
    }

    public void addGrade(Grade grade) {
        this.gradeRepository.addGrade(grade);
    }

    public void updateGrade(int idx, Grade grade) {
        this.gradeRepository.updateGrade(idx, grade);
    }

    public void submitGrade(Grade grade) {
        var idx = this.getGradeIndex(grade.getId());
        if (idx == Constants.NOT_FOUND) {
            this.addGrade(grade);
        } else {
            this.updateGrade(idx, grade);
        }
    }

    public Grade getGradeById(String id) {
        var idx = this.getGradeIndex(id);
        return this.getGrade(idx);
    }

    public int getGradeIndex(String id) {
        List<Grade> grades = this.getGrades();
        for (int i = 0; i < grades.size(); i++) {
            if (grades.get(i).getId().equals(id)) {
                return i;
            }
        }
        return Constants.NOT_FOUND;
    }
}
