package com.spring.todo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.todo.dtos.UpdateUserDto;
import com.spring.todo.entities.User;
import com.spring.todo.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> allUsers() {
		List<User> users = new ArrayList<>();
		this.userRepository.findAll().stream().forEach(item -> users.add(item));
		return users;
	}
	
	public User updateUser(String email, UpdateUserDto dto)  {
		Optional<User> userOptional = this.userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			User currentUser = userOptional.get();
			if (checkUpdateString(currentUser.getUsername(), dto.getUserName())) {
				currentUser.setFullName(dto.getFullName());
			}
			if (checkUpdateString(currentUser.getFullName(), dto.getFullName())) {
				currentUser.setUserName(dto.getUserName());
			}
			String encodedPassword = passwordEncoder.encode(dto.getPassword());
			if (checkUpdateString(currentUser.getPassword(), encodedPassword)) {
				currentUser.setPassword(encodedPassword);
			}
			return this.userRepository.save(currentUser);
		}
		throw new IllegalStateException("User doesn't exist");
	}
	
	private boolean checkUpdateString(String oldString, String updatedString) {
		if (updatedString != null && !updatedString.isEmpty() && !updatedString.equals(oldString)) {
			return true;
		}
		return false;
	}
}
