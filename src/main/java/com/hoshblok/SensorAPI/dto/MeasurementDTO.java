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
	private SensorDTO sensor;

	public MeasurementDTO(Float value, Boolean raining, SensorDTO sensor) {
		this.value = value;
		this.raining = raining;
		this.sensor = sensor;
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

	public SensorDTO getSensor() {
		return sensor;
	}

	public void setSensor(SensorDTO sensor) {
		this.sensor = sensor;
	}
}