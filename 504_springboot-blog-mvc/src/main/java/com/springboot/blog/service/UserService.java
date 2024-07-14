package com.springboot.blog.service;

import com.springboot.blog.dto.RegistrationDto;
import com.springboot.blog.entity.User;

public interface UserService {
    public void saveUser(RegistrationDto registrationDto);

    public User findByEmail(String email);
}
