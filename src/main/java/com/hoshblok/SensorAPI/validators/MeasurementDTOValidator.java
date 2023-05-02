package com.hoshblok.SensorAPI.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.MeasurementDTO;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class MeasurementDTOValidator implements Validator{
	
	private final SensorsRepository sensorsRepository;

	public MeasurementDTOValidator(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MeasurementDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MeasurementDTO measurementDTO = (MeasurementDTO) target;
		
		if (!sensorsRepository.findByUsername(measurementDTO.getSensorName()).isPresent()) {
			errors.rejectValue("sensorName", "", "Such sensor is not registered!");
		}
	}
}
