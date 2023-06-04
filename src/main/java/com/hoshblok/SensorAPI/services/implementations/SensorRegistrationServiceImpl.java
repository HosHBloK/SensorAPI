package com.hoshblok.SensorAPI.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.dto.sensor.request.SensorRegistrationRequest;
import com.hoshblok.SensorAPI.mappings.SensorMapping;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;
import com.hoshblok.SensorAPI.services.interfaces.SensorRegistrationService;

@Service
public class SensorRegistrationServiceImpl implements SensorRegistrationService {

	private final SensorsRepository sensorsRepository;
	private final PasswordEncoder passwordEncoder;
	private final SensorMapping sensorMapping;

	@Autowired
	public SensorRegistrationServiceImpl(PasswordEncoder passwordEncoder, SensorsRepository sensorsRepository, SensorMapping sensorMapping) {
		this.sensorsRepository = sensorsRepository;
		this.passwordEncoder = passwordEncoder;
		this.sensorMapping = sensorMapping;
	}

	@Override
	@Transactional
	public void register(SensorRegistrationRequest request) {
		
		Sensor sensor = sensorMapping.convertToSensor(request);
		sensor.setPassword(passwordEncoder.encode(sensor.getPassword()));
		sensor.setRole("ROLE_SENSOR");
		sensorsRepository.save(sensor);
	}
}