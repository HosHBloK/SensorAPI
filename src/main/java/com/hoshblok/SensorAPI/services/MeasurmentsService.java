package com.hoshblok.SensorAPI.services;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.dto.MeasurementDTO;
import com.hoshblok.SensorAPI.models.Measurement;
import com.hoshblok.SensorAPI.repositories.MeasuremntsRepository;

@Service
@Transactional(readOnly = true)

public class MeasurmentsService {

	private final MeasuremntsRepository measuremntsRepository;
	private final SensorsService sensorsService;
	private final ModelMapper modelMapper;

	@Autowired
	public MeasurmentsService(MeasuremntsRepository measuremntsRepository, SensorsService sensorsService, ModelMapper modelMapper) {
		this.measuremntsRepository = measuremntsRepository;
		this.sensorsService = sensorsService;
		this.modelMapper = modelMapper;
	}

	public List<Measurement> findAll() {

		return measuremntsRepository.findAll();
	}

	public List<Measurement> findAll(String sensorUsername) {
		return measuremntsRepository.findBySensorName(sensorUsername);
	}

	@Transactional
	public void save(Measurement measurement) {
		enrichMeasurement(measurement);
		measuremntsRepository.save(measurement);
	}

	private void enrichMeasurement(Measurement measurement) {
		measurement.setSensor(sensorsService.findOne(measurement.getSensor().getUsername()));
		measurement.setTimestamp(LocalDateTime.now());
	}

	public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {

		MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
		return measurementDTO;
	}

	public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
		Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
		measurement.setSensor(sensorsService.findOne(measurement.getSensorName()));
		return measurement;
	}
}
