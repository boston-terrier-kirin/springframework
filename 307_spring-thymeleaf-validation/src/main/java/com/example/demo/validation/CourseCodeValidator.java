package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseCodeValidator implements ConstraintValidator<CourseCode, String> {

    private String coursePrefix;

    @Override
    public void initialize(CourseCode constraintAnnotation) {
        this.coursePrefix = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String courseCode, ConstraintValidatorContext constraintValidatorContext) {

        if (courseCode == null) {
            return false;
        }

        if (courseCode.startsWith(this.coursePrefix)) {
            return true;
        }

        return false;
    }
}
