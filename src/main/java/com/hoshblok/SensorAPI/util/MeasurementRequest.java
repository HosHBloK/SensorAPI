package com.hoshblok.SensorAPI.util;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MeasurementRequest {
	
	@NotEmpty(message = "Sensor username should not be empty!")
	@Size(min = 3, max = 30, message = "Sensor username should be between 3 and 30 symbols!")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}
	

}
