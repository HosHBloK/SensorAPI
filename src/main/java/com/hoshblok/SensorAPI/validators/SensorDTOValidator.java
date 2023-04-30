package com.hoshblok.SensorAPI.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class SensorDTOValidator implements Validator{
	
	private final SensorsRepository sensorsRepository;

	public SensorDTOValidator(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SensorDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SensorDTO sensorDTO = (SensorDTO) target;
		
		if (sensorsRepository.findByName(sensorDTO.getName()).isPresent()) {
			errors.rejectValue("name", "", "This sensor name is already taken!");
		}
	}
}
