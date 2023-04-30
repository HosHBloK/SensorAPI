package com.hoshblok.SensorAPI.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Service
@Transactional(readOnly = true)
public class SensorsService {

	private final SensorsRepository sensorsRepository;

	@Autowired
	public SensorsService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}

	public List<Sensor> findAll() {
		return sensorsRepository.findAll();
	}

	@Transactional
	public void save(Sensor sendor) {
		sensorsRepository.save(sendor);
	}

	public Sensor findOne(String name) {
		return sensorsRepository.findByName(name).orElse(null);
	}
}
