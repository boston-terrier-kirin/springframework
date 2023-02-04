package com.example.demo.service;

import com.example.demo.request.TodoRequest;
import com.example.demo.response.TodoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodosService {

    private static List<TodoResponse> todoResponses = new ArrayList<>();

    private static Long todosCount = 0L;

    static {
        todoResponses.add(new TodoResponse(++todosCount, "kirin","Get AWS Certified",
                LocalDate.now().plusYears(10), false ));
        todoResponses.add(new TodoResponse(++todosCount, "kirin","Learn DevOps",
                LocalDate.now().plusYears(11), false ));
        todoResponses.add(new TodoResponse(++todosCount, "kirin","Learn Full Stack Development",
                LocalDate.now().plusYears(12), false ));
    }

    public List<TodoResponse> findByUsername(String username){
        Predicate<? super TodoResponse> predicate =
                todoResponse -> todoResponse.getUsername().equalsIgnoreCase(username);
        return todoResponses.stream().filter(predicate).toList();
    }

    public TodoResponse addTodo(String username, String description, LocalDate targetDate, boolean done) {
        TodoResponse todoResponse = new TodoResponse(++todosCount,username,description,targetDate,done);
        todoResponses.add(todoResponse);
        return todoResponse;
    }

    public void deleteById(Long id) {
        Predicate<? super TodoResponse> predicate = todoResponse -> todoResponse.getId() == id;
        todoResponses.removeIf(predicate);
    }

    public TodoResponse findById(Long id) {
        Predicate<? super TodoResponse> predicate = todoResponse -> todoResponse.getId() == id;
        TodoResponse todoResponse = todoResponses.stream().filter(predicate).findFirst().get();
        return todoResponse;
    }

    public TodoRequest updateTodo(Long id, TodoRequest todoRequest) {
        deleteById(id);
        var todo = new TodoResponse(todoRequest.getId(), todoRequest.getUsername(), todoRequest.getDescription(), todoRequest.getTargetDate(), todoRequest.isDone());
        todoResponses.add(todo);

        return todoRequest;
    }
}