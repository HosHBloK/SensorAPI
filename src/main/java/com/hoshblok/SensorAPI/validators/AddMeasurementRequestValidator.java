package com.hoshblok.SensorAPI.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.measurement.request.AddMeasurementRequest;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class AddMeasurementRequestValidator implements Validator{ 

	private final SensorsRepository sensorsRepository;

	public AddMeasurementRequestValidator(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AddMeasurementRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AddMeasurementRequest addMeasurementRequest = (AddMeasurementRequest) target;
		
		if (sensorsRepository.findByUsername(addMeasurementRequest.getUsername()).isEmpty()) {
			errors.rejectValue("username", "", "Sensor with this username is not registered!");
		}
	}
}