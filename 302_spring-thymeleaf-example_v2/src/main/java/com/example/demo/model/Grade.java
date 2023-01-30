package com.example.demo.model;

import com.example.demo.validator.Score;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class Grade {
    private String id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    @Score(message = "Score must be a letter grade")
    private String score;

    public Grade() {
        this.id = UUID.randomUUID().toString();
    }

    public Grade(String name, String subject, String score) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.subject = subject;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
