package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.response.UserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

	@GetMapping("/status/check")
	public String status() {
		return "Working...";
	}

	// @PreAuthorize("hasRole('developer')")
	@Secured("ROLE_developer")
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		return "Deleted user with id: " + id + " subject: " + jwt.getSubject();
	}

	// @PostAuthorizeはメソッド自体は実行されてしまうので、イマイチ用途が不明
	// @PostAuthorize("returnObject.userId == #jwt.subject")
	@PreAuthorize("#id == #jwt.subject")
	@GetMapping("/{id}")
	public UserResponse getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
		System.out.println("★getUser");
		System.out.println(id);
		System.out.println(jwt.getSubject());
		return new UserResponse(id, "Kohei", "Matsumoto");
	}

	@GetMapping
	public List<UserResponse> getUsers(Model model, @AuthenticationPrincipal Jwt jwt) {
		System.out.println("★getUsesr");
		System.out.println(jwt.getSubject());
		
		List<UserResponse> users = new ArrayList<>();
		UserResponse john = new UserResponse("1", "John", "Doe");
		UserResponse jane = new UserResponse("1", "Jane", "Doe");
		users.add(john);
		users.add(jane);

		return users;
	}
}
