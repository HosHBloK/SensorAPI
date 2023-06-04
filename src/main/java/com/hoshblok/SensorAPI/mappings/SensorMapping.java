package com.hoshblok.SensorAPI.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoshblok.SensorAPI.dto.sensor.request.SensorRegistrationRequest;
import com.hoshblok.SensorAPI.models.Sensor;

@Component
public class SensorMapping {
	
	private final ModelMapper modelMapper;

	@Autowired
	public SensorMapping(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Sensor convertToSensor(SensorRegistrationRequest request) {
		
		return modelMapper.map(request, Sensor.class);
	}
}