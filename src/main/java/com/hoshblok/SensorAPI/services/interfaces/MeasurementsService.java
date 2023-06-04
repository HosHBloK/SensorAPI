package com.hoshblok.SensorAPI.services.interfaces;

import java.util.List;

import com.hoshblok.SensorAPI.dto.measurement.request.AddMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.request.GetMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.response.MeasurementResponse;
import com.hoshblok.SensorAPI.dto.measurement.response.RainyDaysResponse;
import com.hoshblok.SensorAPI.models.Measurement;

public interface MeasurementsService {

	List<Measurement> findAll();

	List<Measurement> findAll(String username);

	List<MeasurementResponse> getMeasurements(GetMeasurementRequest request);

	RainyDaysResponse getRainyDaysCount(GetMeasurementRequest request);

	void save(AddMeasurementRequest request);
}