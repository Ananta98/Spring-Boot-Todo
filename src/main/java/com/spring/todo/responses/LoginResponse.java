package com.spring.todo.responses;

import java.util.Date;

public class LoginResponse {
	private String token;
	private Long expiresIn;
	
	public LoginResponse() {}
	
	public LoginResponse(String token, Long expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
