package com.springboot.blog.service.impl;

import com.springboot.blog.dto.RegistrationDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        User user = new User();
        user.setName(registrationDto.getFirstName() + " " + registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(this.passwordEncoder().encode(registrationDto.getPassword()));

        Role role = this.roleRepository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));

        this.userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
