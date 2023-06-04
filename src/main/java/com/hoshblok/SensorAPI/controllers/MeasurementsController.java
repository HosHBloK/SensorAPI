package com.hoshblok.SensorAPI.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoshblok.SensorAPI.dto.measurement.request.AddMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.request.GetMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.response.MeasurementResponse;
import com.hoshblok.SensorAPI.dto.measurement.response.RainyDaysResponse;
import com.hoshblok.SensorAPI.errors.ErrorMessage;
import com.hoshblok.SensorAPI.exceptions.NotValidMeasurementException;
import com.hoshblok.SensorAPI.services.interfaces.MeasurementsService;
import com.hoshblok.SensorAPI.validators.AddMeasurementRequestValidator;
import com.hoshblok.SensorAPI.validators.GetMeasurementRequestValidator;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

	private final MeasurementsService measurmentsService;
	private final AddMeasurementRequestValidator addValidator;
	private final GetMeasurementRequestValidator getValidator;
	
	@Autowired
	public MeasurementsController(MeasurementsService measurmentsService, AddMeasurementRequestValidator addValidator, GetMeasurementRequestValidator getValidator) {
		this.measurmentsService = measurmentsService;
		this.addValidator = addValidator;
		this.getValidator = getValidator;
	}

	@PostMapping("/get")
	public List<MeasurementResponse> getMeasurementsOfSensor(@RequestBody @Valid GetMeasurementRequest request,
		BindingResult bindingResult) {

		getValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidMeasurementException(ErrorMessage.getErrorMessage(bindingResult));
		}

		return measurmentsService.getMeasurements(request);
	}

	@PostMapping("/rainy_days_count")
	public RainyDaysResponse countRainyDaysOfSensor(@RequestBody @Valid GetMeasurementRequest request,
		BindingResult bindingResult) {

		getValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidMeasurementException(ErrorMessage.getErrorMessage(bindingResult));
		}

		return measurmentsService.getRainyDaysCount(request);
	}

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid AddMeasurementRequest request,
		BindingResult bindingResult) {

		addValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidMeasurementException(ErrorMessage.getErrorMessage(bindingResult));
		}

		measurmentsService.save(request);

		return ResponseEntity.ok(HttpStatus.OK);
	}	
}