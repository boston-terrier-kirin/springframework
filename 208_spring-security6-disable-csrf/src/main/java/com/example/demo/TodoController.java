package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    private List<Todo> todoList = List.of(
            new Todo("kirin", "React"),
                new Todo("kuroro", "Vue")
        );

    @GetMapping("/token")
    public CsrfToken token(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

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
}

record Todo(String username, String description){}