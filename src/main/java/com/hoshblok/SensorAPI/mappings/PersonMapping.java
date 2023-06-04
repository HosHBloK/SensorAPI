package com.hoshblok.SensorAPI.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoshblok.SensorAPI.dto.person.request.PersonRegistrationRequest;
import com.hoshblok.SensorAPI.models.Person;

@Component
public class PersonMapping {
	
	private final ModelMapper modelMapper;
	
	@Autowired
	public PersonMapping(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Person convertToPerson(PersonRegistrationRequest request) {

		return modelMapper.map(request, Person.class);
	}
}