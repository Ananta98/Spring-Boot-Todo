package com.spring.todo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.dtos.UpdateUserDto;
import com.spring.todo.entities.User;
import com.spring.todo.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/about-me")
	public ResponseEntity<User> aboutMe() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		return ResponseEntity.ok(currentUser);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<User>> allUsers() {
		List<User> users = userService.allUsers();
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/update-me")
	public ResponseEntity<User> updateUser(@RequestBody UpdateUserDto updatedUserDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User)auth.getPrincipal();
		User updatedUser = this.userService.updateUser(currentUser.getEmail(), updatedUserDto);
		return ResponseEntity.ok(updatedUser);
	}
}
