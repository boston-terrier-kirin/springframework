package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.UserServiceException;
import com.example.model.request.UserDetailsRequest;
import com.example.model.response.ErrorMessages;
import com.example.model.response.OperationStatusResponse;
import com.example.model.response.UserDetailsResponse;
import com.example.service.UserService;
import com.example.shared.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserDetailsResponse> getUsers(@RequestParam(value="page", defaultValue="0") int page,
												@RequestParam(value="limit", defaultValue="20") int limit) {

		List<UserDto> users = this.userService.getUsers(page, limit);
		List<UserDetailsResponse> returnValue = new ArrayList<>();
		
		for (UserDto userDto : users) {
			UserDetailsResponse user = new UserDetailsResponse();
			BeanUtils.copyProperties(userDto, user);
			returnValue.add(user);
		}

		return returnValue;
	}

	@GetMapping(path="/{userId}")
	public UserDetailsResponse getUser(@PathVariable String userId) {
		UserDto userDto = this.userService.getUserByUserId(userId);

		UserDetailsResponse returnValue = new UserDetailsResponse();
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	@PostMapping
	public UserDetailsResponse createUser(@RequestBody UserDetailsRequest userDetails) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		if (userDto.getFirstName().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}

		UserDto createdUser = this.userService.createUser(userDto);

		UserDetailsResponse returnValue = new UserDetailsResponse();
		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@PutMapping(path="/{userId}")
	public UserDetailsResponse updateUser(@PathVariable String userId, @RequestBody UserDetailsRequest userDetails) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updatedUser = this.userService.updateUser(userId, userDto);

		UserDetailsResponse returnValue = new UserDetailsResponse();
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@DeleteMapping(path="/{userId}")
	public OperationStatusResponse deleteUser(@PathVariable String userId) {
	
		this.userService.deleteUser(userId);

		OperationStatusResponse response = new OperationStatusResponse();
		response.setOperationStatus(HttpStatus.OK.name());
		response.setOperationName("DELETE");
		
		return response;
	}
}
