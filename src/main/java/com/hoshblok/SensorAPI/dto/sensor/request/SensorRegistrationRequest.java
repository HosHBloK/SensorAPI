package com.hoshblok.SensorAPI.dto.sensor.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SensorRegistrationRequest {
	
	@NotNull(message = "Username should not be null!")
	@Size(min = 3, max = 30, message = "Username should be between 3 and 30 characters!")
	private String username;

	@NotNull(message = "Password should not be null!")
	private String password;

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
}