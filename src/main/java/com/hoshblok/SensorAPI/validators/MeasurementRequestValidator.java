package com.hoshblok.SensorAPI.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.repositories.SensorsRepository;
import com.hoshblok.SensorAPI.util.MeasurementRequest;

@Component
public class MeasurementRequestValidator implements Validator{
	
	private final SensorsRepository sensorsRepository;

	public MeasurementRequestValidator(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MeasurementRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MeasurementRequest measurementRequest = (MeasurementRequest) target;
		
		if (!sensorsRepository.findByUsername(measurementRequest.getUsername()).isPresent()) {
			errors.rejectValue("username", "", "Sensor with this name is not found!");
		}
	}

}
