package com.hoshblok.SensorAPI.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoshblok.SensorAPI.dto.person.request.PersonRegistrationRequest;
import com.hoshblok.SensorAPI.mappings.PersonMapping;
import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;
import com.hoshblok.SensorAPI.services.interfaces.PersonRegistrationService;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {
	
	private final PeopleRepository peopleRepository;
	private final PasswordEncoder passwordEncoder;
	private final PersonMapping personMapping;
	
	@Autowired
	public PersonRegistrationServiceImpl(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository, PersonMapping personMapping) {
		this.peopleRepository = peopleRepository;
		this.passwordEncoder = passwordEncoder;
		this.personMapping = personMapping;
	}
	
	@Override
	@Transactional
	public void register(PersonRegistrationRequest request) {
		
		Person person = personMapping.convertToPerson(request);
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		person.setRole("ROLE_PERSON");
		peopleRepository.save(person);
	}
}