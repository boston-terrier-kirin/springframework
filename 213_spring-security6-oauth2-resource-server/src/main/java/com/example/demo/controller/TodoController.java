package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    private List<Todo> todoList = List.of(
            new Todo("kirin", "React"),
            new Todo("kuroro", "Vue")
    );

    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        return todoList;
    }

    @GetMapping("/users/{username}/todos")
    public List<Todo> getUserTodos(@PathVariable String username) {
        return todoList
                .stream()
                .filter(todo -> todo.username().equals(username))
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{username}/todos")
    public void createUserTodo(@PathVariable String username, @RequestBody Todo todo) {
        System.out.println(todo);
    }


    @GetMapping("/admin")
    public String getAdmin(Authentication authentication) {
        return "ADMIN";
    }

    @GetMapping("/user")
    public String getUser() {
        return "USER";
    }
}

record Todo(String username, String description){}
