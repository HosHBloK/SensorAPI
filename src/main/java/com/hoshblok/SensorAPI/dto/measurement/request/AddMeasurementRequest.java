package com.hoshblok.SensorAPI.dto.measurement.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddMeasurementRequest {

	@NotNull(message = "Value should not be empty")
	@Pattern(regexp = "^\\d*\\.?\\d+$", message = "Field 'value' must contain only numbers")
	@Min(value = -100, message = "Value should be above -100!")
	@Max(value = 100, message = "Value should be under 100!")
	private String value;

	@NotNull(message = "Raining should not be empty")
	@Pattern(regexp = "^(true|false)$", message = "Field 'value' must be either 'true' or 'false'")
	private String raining;

	@NotNull(message = "Sensor username should not be empty")
	private String username;

	public AddMeasurementRequest(String value, String raining, String username) {
		this.value = value;
		this.raining = raining;
		this.username = username;
	}

	public AddMeasurementRequest() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setRaining(String raining) {
		this.raining = raining;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRaining() {
		return raining;
	}
}