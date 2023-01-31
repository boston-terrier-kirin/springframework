package com.example.service;


import com.example.entity.User;

public interface UserService {
    User getUser(Long id);
    User saveUser(User user);
}