package com.hoshblok.SensorAPI.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hoshblok.SensorAPI.exceptions.PersonNotFoundException;
import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;
import com.hoshblok.SensorAPI.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService {

	private PeopleRepository peopleRepository;

	@Autowired
	public PersonDetailsService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username){
		
		Optional<Person> person =  peopleRepository.findByUsername(username);
		
		if(person.isEmpty()) {
			throw new PersonNotFoundException("Person with this username is not found!");
		}
		
		return new PersonDetails(person.get());
	}
}