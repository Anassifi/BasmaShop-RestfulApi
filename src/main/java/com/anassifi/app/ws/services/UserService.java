package com.anassifi.app.ws.services;

import com.anassifi.app.ws.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto userDto);
}
