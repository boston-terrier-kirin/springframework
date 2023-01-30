package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class ScoreValidator implements ConstraintValidator<Score, String> {

    List<String> scores = Arrays.asList(
            "A", "A+", "A-",
            "B", "B+", "B-",
            "C", "C+", "C-",
            "D", "D+", "D-",
            "E", "E+", "E-",
            "F"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (String score : scores) {
            if (value.equals(score)) {
                return true;
            }
        }

        return false;
    }
}
