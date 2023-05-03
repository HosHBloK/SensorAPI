package com.hoshblok.SensorAPI.controllers;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoshblok.SensorAPI.dto.MeasurementDTO;
import com.hoshblok.SensorAPI.exceptions.NotValidMeasurementException;
import com.hoshblok.SensorAPI.exceptions.SensorNotFoundException;
import com.hoshblok.SensorAPI.models.Measurement;
import com.hoshblok.SensorAPI.services.MeasurmentsService;
import com.hoshblok.SensorAPI.util.ErrorMessage;
import com.hoshblok.SensorAPI.util.ErrorResponse;
import com.hoshblok.SensorAPI.util.MeasurementRequest;
import com.hoshblok.SensorAPI.util.MeasurementResponse;
import com.hoshblok.SensorAPI.validators.MeasurementDTOValidator;
import com.hoshblok.SensorAPI.validators.MeasurementRequestValidator;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

	private final MeasurmentsService measurmentsService;
	private final MeasurementDTOValidator measurementDTOValidator;
	private final MeasurementRequestValidator measurementRequestValidator;

	@Autowired
	public MeasurementsController(MeasurmentsService measurmentsService,
		MeasurementDTOValidator measurementDTOValidator, MeasurementRequestValidator measurementRequestValidator) {
		this.measurmentsService = measurmentsService;
		this.measurementDTOValidator = measurementDTOValidator;
		this.measurementRequestValidator = measurementRequestValidator;
	}

	@PostMapping("/get")
	public MeasurementResponse getMeasurementsOfSensor(@RequestBody @Valid MeasurementRequest measurementRequest,
		BindingResult bindingResult) {

		measurementRequestValidator.validate(measurementRequest, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new SensorNotFoundException(ErrorMessage.getErrorMessage(bindingResult));
		}

		return new MeasurementResponse(measurmentsService.findAll(measurementRequest.getUsername()).stream().map(
			measurmentsService::convertToMeasurementDTO).collect(Collectors.toList()));

	}

	@PostMapping("/rainy_days_count")
	public Long countRainyDaysOfSensor(@RequestBody @Valid MeasurementRequest measurementRequest,
		BindingResult bindingResult) {

		measurementRequestValidator.validate(measurementRequest, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new SensorNotFoundException(ErrorMessage.getErrorMessage(bindingResult));
		}

		return measurmentsService.findAll(measurementRequest.getUsername()).stream().filter(Measurement::isRaining)
			.count();
	}

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
		BindingResult bindingResult) {

		measurementDTOValidator.validate(measurementDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidMeasurementException(ErrorMessage.getErrorMessage(bindingResult));
		}

		measurmentsService.save(measurmentsService.convertToMeasurement(measurementDTO));

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleCreateException(NotValidMeasurementException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleCreateException(SensorNotFoundException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
