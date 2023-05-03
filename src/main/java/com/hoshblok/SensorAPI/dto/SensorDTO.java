package com.hoshblok.SensorAPI.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SensorDTO {

	@NotEmpty(message = "Sensor username should not be empty!")
	@Size(min = 3, max = 30, message = "Sensor username should be between 3 and 30 symbols!")
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