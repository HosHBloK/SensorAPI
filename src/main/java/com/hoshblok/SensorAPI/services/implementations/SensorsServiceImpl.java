package com.hoshblok.SensorAPI.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;
import com.hoshblok.SensorAPI.services.interfaces.SensorsService;

@Service
@Transactional(readOnly = true)
public class SensorsServiceImpl implements SensorsService {

	private final SensorsRepository sensorsRepository;
	
	@Autowired
	public SensorsServiceImpl(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}

	@Override
	public Sensor findOne(String name) {
		return sensorsRepository.findByUsername(name).orElse(null);
	}
}