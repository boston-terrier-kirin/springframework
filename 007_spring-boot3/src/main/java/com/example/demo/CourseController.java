package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CurrencyServiceConfig currencyServiceConfig;

    @RequestMapping("/courses")
    public List<Course> findAllCourses() {
        return Arrays.asList(
                new Course(1, "Learn React", "in28minutes"),
                new Course(2, "Learn Angular", "in28minites"),
                new Course(3, "Learn Vue", "in28minites"),
                new Course(4, "Learn Springframework", "in28minites")
        );
    }

    @RequestMapping("/currency")
    public CurrencyServiceConfig findConfig() {
        return this.currencyServiceConfig;
    }
}
