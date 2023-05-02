package com.hoshblok.SensorAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Service
public class SensorRegistrationService {

	private final SensorsRepository sensorsRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SensorRegistrationService(PasswordEncoder passwordEncoder, SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void register(Sensor sensor) {
		sensor.setPassword(passwordEncoder.encode(sensor.getPassword()));
		sensor.setRole("ROLE_SENSOR");
		sensorsRepository.save(sensor);
	}
}
