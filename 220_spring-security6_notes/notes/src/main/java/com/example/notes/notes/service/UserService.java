package com.example.notes.notes.service;

import com.example.notes.notes.dto.UserDto;
import com.example.notes.notes.model.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDto getUserById(Long id);

    User findByUsername(String username);
}