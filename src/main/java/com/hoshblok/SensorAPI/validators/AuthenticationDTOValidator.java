package com.hoshblok.SensorAPI.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.AuthenticationDTO;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class AuthenticationDTOValidator implements Validator {

	private final PeopleRepository peopleRepository;
	private final SensorsRepository sensorsRepository;

	public AuthenticationDTOValidator(PeopleRepository peopleRepository, SensorsRepository sensorsRepository) {
		this.peopleRepository = peopleRepository;
		this.sensorsRepository = sensorsRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthenticationDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuthenticationDTO authenticationDTO = (AuthenticationDTO) target;

		if (authenticationDTO.getRole() != null && !authenticationDTO.getRole().equalsIgnoreCase("person")
			&& !authenticationDTO.getRole().equalsIgnoreCase("sensor")) {
			errors.rejectValue("role", "", "Unknown role! Use 'person' or 'sensor'.");
		}

		if (authenticationDTO.getUsername() == null || authenticationDTO.getRole() == null) {
			return;
		}
		if (authenticationDTO.getRole().equalsIgnoreCase("sensor") && sensorsRepository.findByUsername(authenticationDTO
			.getUsername()).isEmpty()) {
			errors.rejectValue("username", "", "Such sensor is not registered!");
		}
		if (authenticationDTO.getRole().equalsIgnoreCase("person") && peopleRepository.findByUsername(authenticationDTO
			.getUsername()).isEmpty()) {
			errors.rejectValue("username", "", "Such person is not registered!");
		}
	}
}
