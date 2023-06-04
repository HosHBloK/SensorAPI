package com.hoshblok.SensorAPI.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.auth.request.LoginRequest;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;
import com.hoshblok.SensorAPI.repositories.SensorsRepository;

@Component
public class LoginRequestValidator implements Validator {

	private final PeopleRepository peopleRepository;
	private final SensorsRepository sensorsRepository;

	@Autowired
	public LoginRequestValidator(PeopleRepository peopleRepository, SensorsRepository sensorsRepository) {
		this.peopleRepository = peopleRepository;
		this.sensorsRepository = sensorsRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {

		return LoginRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		LoginRequest loginRequest = (LoginRequest) target;

		if (loginRequest.getRole().equals("person") && peopleRepository.findByUsername(loginRequest.getUsername())
			.isEmpty()) {

			errors.rejectValue("username", "", "Person with this username is not registered!");
		}
		
		if (loginRequest.getRole().equals("sensor") && sensorsRepository.findByUsername(loginRequest.getUsername())
			.isEmpty()) {

			errors.rejectValue("username", "", "Sensor with this username is not registered!");
		}
	}
}