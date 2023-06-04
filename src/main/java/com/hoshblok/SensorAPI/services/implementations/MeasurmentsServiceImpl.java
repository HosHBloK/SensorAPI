package com.hoshblok.SensorAPI.services.implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.dto.measurement.request.AddMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.request.GetMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.response.MeasurementResponse;
import com.hoshblok.SensorAPI.dto.measurement.response.RainyDaysResponse;
import com.hoshblok.SensorAPI.mappings.MeasurementMapping;
import com.hoshblok.SensorAPI.models.Measurement;
import com.hoshblok.SensorAPI.repositories.MeasuremntsRepository;
import com.hoshblok.SensorAPI.services.interfaces.MeasurementsService;
import com.hoshblok.SensorAPI.services.interfaces.SensorsService;

@Service
@Transactional(readOnly = true)
public class MeasurmentsServiceImpl implements MeasurementsService {

	private final MeasuremntsRepository measuremntsRepository;
	private final SensorsService sensorsService;
	private final MeasurementMapping measurementMapping;

	@Autowired
	public MeasurmentsServiceImpl(MeasuremntsRepository measuremntsRepository, SensorsService sensorsService,
		MeasurementMapping measurementMapping) {
		this.measuremntsRepository = measuremntsRepository;
		this.sensorsService = sensorsService;
		this.measurementMapping = measurementMapping;
	}

	@Override
	public List<Measurement> findAll() {

		return measuremntsRepository.findAll();
	}

	@Override
	public List<Measurement> findAll(String username) {

		return measuremntsRepository.findByUsername(username);
	}

	@Override
	public List<MeasurementResponse> getMeasurements(GetMeasurementRequest request) {

		return measurementMapping.convertToMeasurementResponseList(findAll(request.getUsername()));
	}

	@Override
	public RainyDaysResponse getRainyDaysCount(GetMeasurementRequest request) {

		return new RainyDaysResponse(findAll(request.getUsername()).stream().filter(Measurement::isRaining).count());
	}

	@Override
	@Transactional
	public void save(AddMeasurementRequest request) {

		Measurement measurement = measurementMapping.convertToMeasurement(request);
		enrichMeasurement(measurement, request.getUsername());
		measuremntsRepository.save(measurement);
	}

	private void enrichMeasurement(Measurement measurement, String sensorUsername) {

		measurement.setSensor(sensorsService.findOne(sensorUsername));
		measurement.setTimestamp(LocalDateTime.now());
	}
}