package com.spring.todo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.todo.dtos.LoginUserDto;
import com.spring.todo.dtos.RegisterUserDto;
import com.spring.todo.entities.User;
import com.spring.todo.responses.LoginResponse;
import com.spring.todo.services.AuthenticationService;
import com.spring.todo.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	private final JwtService jwtService;
	private final AuthenticationService authService;
	
	public AuthenticationController(
		JwtService jwtService,
		AuthenticationService authService
	) {
		this.jwtService = jwtService;
		this.authService = authService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody RegisterUserDto user) {
		User createdNewUser = authService.createNewUser(user);
		return ResponseEntity.ok(createdNewUser);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto login) {
		User authenticatedUser = authService.authenticateUser(login);
		String token = jwtService.generateToken(authenticatedUser);
		LoginResponse response = new LoginResponse();
		response.setToken(token);
		response.setExpiresIn(jwtService.getExpirationTime());
		return ResponseEntity.ok(response);
	}
}
