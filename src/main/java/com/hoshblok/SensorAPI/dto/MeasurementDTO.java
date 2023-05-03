package com.hoshblok.SensorAPI.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MeasurementDTO {

	@NotNull(message = "Value should not be empty")
	@Pattern(regexp = "^\\d+$", message = "Field 'value' must contain only numbers")
	@Min(value = -100, message = "Value should be above -100!")
	@Max(value = 100, message = "Value should be under 100!")
	private String value;

	@NotNull(message = "Raining should not be empty")
	@Pattern(regexp = "^(true|false)$", message = "Field 'value' must be either 'true' or 'false'")
	private String raining;

	@NotNull(message = "Sensor username should not be empty")
	private String sensorName;

	public MeasurementDTO(String value, String raining, String sensorName) {
		this.value = value;
		this.raining = raining;
		this.sensorName = sensorName;
	}

	public MeasurementDTO() {
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

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getRaining() {
		return raining;
	}
}