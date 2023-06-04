package com.hoshblok.SensorAPI.dto.auth.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginRequest {

	@NotNull(message = "Username should not be null!")
	@Size(min = 3, max = 30, message = "Username should be between 3 and 30 characters!")
	private String username;

	@NotNull(message = "Password should not be null!")
	private String password;

	public LoginRequest(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public LoginRequest() {}

	@NotNull(message = "Role should not be null!")
	@Pattern(regexp = "^(person|sensor)$", message = "Field 'role' must be either 'person' or 'sensor'")
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