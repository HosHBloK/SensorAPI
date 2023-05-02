package com.hoshblok.SensorAPI.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthenticationDTO {

	@NotNull(message = "Username should not be null!")
	@Size(min = 3, max = 30, message = "Username should be between 3 and 30 characters!")
	private String username;

	@NotNull(message = "Password should not be null!")
	private String password;
	
	@NotNull(message = "Role should not be null!")
	@Size(min = 4, max = 6, message = "Role should be between 4 and 6 characters!")
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
