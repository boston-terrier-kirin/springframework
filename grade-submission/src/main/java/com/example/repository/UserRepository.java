package com.example.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByUsername(String username);
}