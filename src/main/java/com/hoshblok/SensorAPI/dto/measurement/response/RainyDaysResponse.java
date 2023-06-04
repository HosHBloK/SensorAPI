package com.hoshblok.SensorAPI.dto.measurement.response;

public class RainyDaysResponse {

	private long rainy_days_count;

	public RainyDaysResponse(long rainy_days_count) {
		this.rainy_days_count = rainy_days_count;
	}

	public RainyDaysResponse() {
	}

	public long getRainy_days_count() {
		return rainy_days_count;
	}

	public void setRainy_days_count(long rainy_days_count) {
		this.rainy_days_count = rainy_days_count;
	}
}