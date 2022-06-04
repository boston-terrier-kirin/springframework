package com.example.spring_jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Test {
    @Id
    private int id;

    private String name;
    private String message;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
