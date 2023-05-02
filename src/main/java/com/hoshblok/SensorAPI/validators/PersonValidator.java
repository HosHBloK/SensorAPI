package com.hoshblok.SensorAPI.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;

@Component
public class PersonValidator implements Validator{
	
	private final PeopleRepository peopleRepository;

	@Autowired
	public PersonValidator(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
	return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		if (peopleRepository.findByUsername(person.getUsername()).isPresent()) {
			errors.rejectValue("username", "", "Username is already taken!");
		}
	}

}

