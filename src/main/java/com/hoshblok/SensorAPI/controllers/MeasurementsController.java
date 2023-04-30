package com.hoshblok.SensorAPI.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoshblok.SensorAPI.dto.MeasurementDTO;
import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.exceptions.NotValidMeasurementException;
import com.hoshblok.SensorAPI.models.Measurement;
import com.hoshblok.SensorAPI.services.MeasurmentsService;
import com.hoshblok.SensorAPI.util.ErrorMessage;
import com.hoshblok.SensorAPI.util.MeasurementErrorResponse;
import com.hoshblok.SensorAPI.util.MeasurementResponse;
import com.hoshblok.SensorAPI.validators.MeasurementDTOValidator;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

	private final MeasurmentsService measurmentsService;
	private final ModelMapper modelMapper;
	private final MeasurementDTOValidator measurementDTOValidator;

	@Autowired
	public MeasurementsController(MeasurmentsService measurmentsService, ModelMapper modelMapper,
		MeasurementDTOValidator measurementDTOValidator) {
		this.measurmentsService = measurmentsService;
		this.modelMapper = modelMapper;
		this.measurementDTOValidator = measurementDTOValidator;
	}

	@GetMapping
	public MeasurementResponse getAll() {

		return new MeasurementResponse(measurmentsService.findAll().stream().map(this::convertToMeasurementDTO).collect(
			Collectors.toList()));
	}

	@GetMapping("/rainyDaysCount")
	public Long countRainyDays() {
		
		return measurmentsService.findAll().stream().filter(Measurement::isRaining).count();
	}

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
		BindingResult bindingResult) {

		measurementDTOValidator.validate(measurementDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidMeasurementException(ErrorMessage.getErrorMessage(bindingResult));
		}

		measurmentsService.save(convertToMeasurement(measurementDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@ExceptionHandler
	private ResponseEntity<MeasurementErrorResponse> handleCreateException(NotValidMeasurementException ex) {
		
		MeasurementErrorResponse response = new MeasurementErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
		
		MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
		measurementDTO.setSensor(modelMapper.map(measurement.getSensor(), SensorDTO.class));
		return measurementDTO;
	}

	private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
		
		return modelMapper.map(measurementDTO, Measurement.class);
	}
}
