package com.hoshblok.SensorAPI.dto.measurement.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class GetMeasurementRequest {
	
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