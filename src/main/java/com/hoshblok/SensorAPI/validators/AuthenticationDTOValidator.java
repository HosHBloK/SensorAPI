package com.hoshblok.SensorAPI.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.AuthenticationDTO;

public class AuthenticationDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AuthenticationDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AuthenticationDTO authenticationDTO = (AuthenticationDTO) target;

		if (!authenticationDTO.getRole().equalsIgnoreCase("user") || !authenticationDTO.getRole().equalsIgnoreCase(
			"sensor")) {
			errors.rejectValue("role", "", "Unknown role! Use 'user' or 'sensor'.");
		}
	}

}
