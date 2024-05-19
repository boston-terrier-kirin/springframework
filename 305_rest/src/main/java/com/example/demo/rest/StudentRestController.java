package com.example.demo.rest;

import com.example.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    @PostConstruct
    public void loadData() {
        this.students = new ArrayList<>();
        this.students.add(new Student("Stephen", "Curry"));
        this.students.add(new Student("Clay", "Thompson"));
        this.students.add(new Student("James", "Harden"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return this.students;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        if (studentId > this.students.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found: " + studentId);
        }

        return this.students.get(studentId);
    }
}