package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TodoResponse {
    private Long id;
    private String username;
    private String description;
    private LocalDate targetDate;
    private boolean done;
}
