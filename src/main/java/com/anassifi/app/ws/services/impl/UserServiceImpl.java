package com.anassifi.app.ws.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.anassifi.app.ws.entities.UserEntity;
import com.anassifi.app.ws.repositories.UserRepository;
import com.anassifi.app.ws.services.UserService;
import com.anassifi.app.ws.shared.Utils;
import com.anassifi.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	Utils utils;

	@Override
	public UserDto createUser(UserDto user) {

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());

		if (checkUser != null)
			throw new RuntimeException("User Already exist!");

		// New data layer instantiation
		UserEntity userEntity = new UserEntity();

		BeanUtils.copyProperties(user, userEntity);

		// Adding encrypted password and generated userID attributes
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(utils.generateUserId(32));

		// Saving a new user
		UserEntity newUser = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(newUser, userDto);

		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if(userEntity == null) throw new UsernameNotFoundException(email);


		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

}
