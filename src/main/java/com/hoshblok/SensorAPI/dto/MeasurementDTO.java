package com.hoshblok.SensorAPI.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {

	@NotNull(message = "Value should not be empty")
	@Min(value = -100, message = "Value should be above -100!")
	@Max(value = 100, message = "Value should be under 100!")
	private Float value;

	@NotNull(message = "Raining should not be empty")
	private Boolean raining;

	@NotNull(message = "Sensor should not be empty")
	private String sensorName;

	public MeasurementDTO(Float value, Boolean raining, String sensorName) {
		this.value = value;
		this.raining = raining;
		this.sensorName = sensorName;
	}

	public MeasurementDTO() {
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Boolean isRaining() {
		return raining;
	}

	public void setRaining(Boolean raining) {
		this.raining = raining;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
}