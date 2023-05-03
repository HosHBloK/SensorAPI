package com.hoshblok.SensorAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;

@Service
public class PersonRegistrationService {
	
	private final PeopleRepository peopleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public PersonRegistrationService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void register(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		person.setRole("ROLE_PERSON");
		peopleRepository.save(person);
	}

}
