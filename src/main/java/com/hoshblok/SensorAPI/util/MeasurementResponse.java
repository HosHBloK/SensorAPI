package com.hoshblok.SensorAPI.util;

import java.util.List;

import com.hoshblok.SensorAPI.dto.MeasurementDTO;

public class MeasurementResponse {
	
	private List<MeasurementDTO> measurements;

	public MeasurementResponse(List<MeasurementDTO> measurements) {
		this.measurements = measurements;
	}
	
	public MeasurementResponse() {
	}

	public List<MeasurementDTO> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<MeasurementDTO> measurements) {
		this.measurements = measurements;
	}
}
