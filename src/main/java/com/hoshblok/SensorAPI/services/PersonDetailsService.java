package com.hoshblok.SensorAPI.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.repositories.PeopleRepository;
import com.hoshblok.SensorAPI.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService{

	private PeopleRepository peopleRepository;

	@Autowired
	public PersonDetailsService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person =  peopleRepository.findByUsername(username);
		
		if(person.isEmpty()) {
			throw new UsernameNotFoundException("User not found!");
		}
		
		return new PersonDetails(person.get());
	}

}
