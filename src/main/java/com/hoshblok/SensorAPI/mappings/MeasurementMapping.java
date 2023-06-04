package com.hoshblok.SensorAPI.mappings;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoshblok.SensorAPI.dto.measurement.request.AddMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.response.MeasurementResponse;
import com.hoshblok.SensorAPI.models.Measurement;

@Component
public class MeasurementMapping {
	
	private final ModelMapper modelMapper;

	@Autowired
	public MeasurementMapping(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Measurement convertToMeasurement(AddMeasurementRequest request) {
		
		return modelMapper.map(request, Measurement.class);
	}
	
	public MeasurementResponse convertToMeasurementResponse(Measurement measurement) {
		
		return modelMapper.map(measurement, MeasurementResponse.class);
	}
	
	public List<MeasurementResponse> convertToMeasurementResponseList(List<Measurement> list){
		
		return list.stream().map(this::convertToMeasurementResponse).collect(Collectors.toList());
	}
}