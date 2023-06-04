package com.hoshblok.SensorAPI.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.measurement.request.GetMeasurementRequest;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class GetMeasurementRequestValidator implements Validator{ 

	private final SensorsRepository sensorsRepository;

	public GetMeasurementRequestValidator(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return GetMeasurementRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		GetMeasurementRequest getMeasurementRequest = (GetMeasurementRequest) target;
		
		if (sensorsRepository.findByUsername(getMeasurementRequest.getUsername()).isEmpty()) {
			errors.rejectValue("username", "", "Sensor with this username is not registered!");
		}
	}
}