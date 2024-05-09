
package com.spring.todo.dtos;

public class RegisterUserDto {
	private String email;
	private String userName;
	private String fullName;
	private String password;
	
	public RegisterUserDto() {}
	
	public RegisterUserDto(String email, String userName, String fullName, String password) {
		this.email = email;
		this.userName = userName;
		this.fullName = fullName;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
