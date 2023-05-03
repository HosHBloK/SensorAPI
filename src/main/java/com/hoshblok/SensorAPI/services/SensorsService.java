package com.hoshblok.SensorAPI.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Service
@Transactional(readOnly = true)
public class SensorsService {

	private final SensorsRepository sensorsRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public SensorsService(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
		this.sensorsRepository = sensorsRepository;
		this.modelMapper = modelMapper;
	}

	public List<Sensor> findAll() {
		return sensorsRepository.findAll();
	}

	@Transactional
	public void save(Sensor sendor) {
		sensorsRepository.save(sendor);
	}

	public Sensor findOne(String name) {
		return sensorsRepository.findByUsername(name).orElse(null);
	}

	public Sensor convertToSensor(SensorDTO sensorDTO) {

		return modelMapper.map(sensorDTO, Sensor.class);
	}
}
