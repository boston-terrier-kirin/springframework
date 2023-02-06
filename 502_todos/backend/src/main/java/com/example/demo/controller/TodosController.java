package com.example.demo.controller;

import com.example.demo.request.TodoRequest;
import com.example.demo.response.TodoResponse;
import com.example.demo.service.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class TodosController {

    private final TodosService todosService;

    @GetMapping("/{username}/todos")
    public ResponseEntity<List<TodoResponse>> getTodos(@PathVariable String username) {
        return new ResponseEntity(todosService.findByUsername(username), HttpStatus.OK );
    }

    @GetMapping("/{username}/todos/{id}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable String username, @PathVariable Long id) {
        return new ResponseEntity(todosService.findById(id), HttpStatus.OK );
    }

    @PostMapping("/{username}/todos")
    public ResponseEntity<TodoResponse> addTodo(@PathVariable String username, @RequestBody TodoRequest todoRequest) {
        System.out.println(todoRequest);
        return new ResponseEntity(todosService.addTodo(todoRequest.getUsername(), todoRequest.getDescription(), todoRequest.getTargetDate(), todoRequest.isDone()), HttpStatus.CREATED );
    }

    @DeleteMapping("/{username}/todos/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable String username, @PathVariable Long id) {
        todosService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT );
    }

    @PutMapping("/{username}/todos/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable String username, @PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        return new ResponseEntity(todosService.updateTodo(id, todoRequest), HttpStatus.OK );
    }
}
