package com.spring.todo.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Service;

import com.spring.todo.dtos.LoginUserDto;
import com.spring.todo.dtos.RegisterUserDto;
import com.spring.todo.entities.User;
import com.spring.todo.repositories.UserRepository;

@Service
public class AuthenticationService {
	
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthenticationService( 
		AuthenticationManager authenticationManager,
		UserRepository userRepository,
		PasswordEncoder passwordEncoder
	) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User createNewUser(RegisterUserDto req) {
		User newUser = new User();
		newUser.setUserName(req.getUserName());
		newUser.setFullName(req.getFullName());
		newUser.setEmail(req.getEmail());
		newUser.setPassword(passwordEncoder.encode(req.getPassword()));
		return userRepository.save(newUser);
	}
	
	public User authenticateUser(LoginUserDto req) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
		);
		return userRepository.findByEmail(req.getEmail()).orElseThrow();
	}
}
