package com.anassifi.app.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anassifi.app.ws.requests.UserRequest;
import com.anassifi.app.ws.responses.UserResponse;
import com.anassifi.app.ws.shared.dto.UserDto;

@RestController
@RequestMapping("/users") //localhost:8080/users
public class UserController {
	
	//Http GET
	@GetMapping
	public String getUser() {
		return "get user was called";
	}
	
	//Http POST
	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		
		UserDto userDto = new UserDto();
		
		//Passing data from presentation to service
		BeanUtils.copyProperties(userRequest, userDto);
		
		UserDto createUser = userService.createUser(userDto);
		UserResponse userResponse = new UserResponse();
		
		BeanUtils.copyProperties(createUser, userResponse);
		
		return userResponse;
	}
	
	//Http PUT
	@PutMapping
	public String updateUser() {
		return "update user was called";
	}
	
	//Http DELETE
	@DeleteMapping
	public String deleteUser() {
		return "delete user was called";
	}
	
}
