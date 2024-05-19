package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Test {

    private int id;
    private String name;
    private String message;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
