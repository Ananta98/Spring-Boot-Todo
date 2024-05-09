package com.spring.todo.dtos;

public class UpdateUserDto {
	private String userName;
	private String fullName;
	private String password;
	
	public UpdateUserDto() {}
	
	public UpdateUserDto(String userName, String fullName, String password) {
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
