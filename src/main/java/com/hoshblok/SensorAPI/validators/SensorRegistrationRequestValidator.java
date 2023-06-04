package com.hoshblok.SensorAPI.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.sensor.request.SensorRegistrationRequest;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class SensorRegistrationRequestValidator implements Validator{
	
	private final SensorsRepository sensorsRepository;

	@Autowired
	public SensorRegistrationRequestValidator(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SensorRegistrationRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SensorRegistrationRequest sensorRegistrationRequest = (SensorRegistrationRequest) target;

		if (sensorsRepository.findByUsername(sensorRegistrationRequest.getUsername()).isPresent()) {
			
			errors.rejectValue("username", "", "Username is already taken!");
		}
	}
}