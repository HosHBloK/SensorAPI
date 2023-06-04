package com.hoshblok.SensorAPI.dto.measurement.response;

public class MeasurementResponse {

	private String value;

	private String raining;

	private String username;

	public MeasurementResponse(String value, String raining, String username) {
		this.value = value;
		this.raining = raining;
		this.username = username;
	}

	public MeasurementResponse() {
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

	@Override
	public String toString() {
		return "MeasurementResponse [value=" + value + ", raining=" + raining + ", username=" + username + "]";
	}
}