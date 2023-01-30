package com.example.demo.repository;

import com.example.demo.model.Grade;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GradeRepository {

    private List<Grade> grades = new ArrayList<>();

    public List<Grade> getGrades() {
        return this.grades;
    }

    public Grade getGrade(int idx) {
        return this.grades.get(idx);
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

    public void updateGrade(int idx, Grade grade) {
        this.grades.set(idx, grade);
    }
}
