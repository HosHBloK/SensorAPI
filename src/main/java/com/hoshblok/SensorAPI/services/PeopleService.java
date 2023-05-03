package com.hoshblok.SensorAPI.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hoshblok.SensorAPI.dto.PersonDTO;
import com.hoshblok.SensorAPI.models.Person;

@Service
public class PeopleService {
	
	private final ModelMapper modelMapper;

	public PeopleService(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Person convertToPerson(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Person.class);
	}
}
