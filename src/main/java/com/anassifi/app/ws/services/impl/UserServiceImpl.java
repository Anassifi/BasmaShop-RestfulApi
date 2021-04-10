package com.anassifi.app.ws.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anassifi.app.ws.entities.UserEntity;
import com.anassifi.app.ws.repositories.UserRepository;
import com.anassifi.app.ws.services.UserService;
import com.anassifi.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto user) {

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());

		if(checkUser != null) throw new RuntimeException("User Already exist!");

		// New data layer instantiation
		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(user, userEntity);

		// Adding default attributes 
		userEntity.setEncryptedPassword("encryptedPassword");
		userEntity.setUserId("userId");

		// Saving a new user
		UserEntity newUser = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(newUser, userDto);

		return userDto;
	}

}
