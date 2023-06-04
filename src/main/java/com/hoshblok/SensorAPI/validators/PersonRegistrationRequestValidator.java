package com.hoshblok.SensorAPI.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.dto.person.request.PersonRegistrationRequest;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;

@Component
public class PersonRegistrationRequestValidator implements Validator {

	private final PeopleRepository peopleRepository;

	@Autowired
	public PersonRegistrationRequestValidator(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonRegistrationRequest.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		PersonRegistrationRequest personRegistrationRequest = (PersonRegistrationRequest) target;

		if (peopleRepository.findByUsername(personRegistrationRequest.getUsername()).isPresent()) {
			
			errors.rejectValue("username", "", "Username is already taken!");
		}
	}
}