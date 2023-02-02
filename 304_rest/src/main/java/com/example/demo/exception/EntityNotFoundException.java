package com.example.demo.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id) {
        super("The id '" + id + "' does not exist in our records");
    }
}