package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.data.UsersRepository;
import com.appsdeveloperblog.estore.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UsersRepository usersRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(UsersRepository usersRepository,
                           EmailVerificationService emailVerificationService) {
        this.usersRepository = usersRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public User createUser(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String repeatPassword) {

        if(firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if(lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());

        boolean isCreated = false;
        try {
            isCreated = this.usersRepository.save(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }

        if (!isCreated) {
            throw new UserServiceException("Could not create user");
        }

        try {
            this.emailVerificationService.scheduleEmailConfirmation(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }

        return user;
    }
}
