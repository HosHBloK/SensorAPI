package com.hoshblok.SensorAPI.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.exceptions.NotValidSensorException;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.services.SensorsService;
import com.hoshblok.SensorAPI.util.ErrorMessage;
import com.hoshblok.SensorAPI.util.SensorErrorResponse;
import com.hoshblok.SensorAPI.validators.SensorDTOValidator;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

	private final SensorsService sensorsService;
	private final ModelMapper modelMapper;
	private final SensorDTOValidator sensorDTOValidator;

	public SensorsController(SensorsService sensorsService, ModelMapper modelMapper,
		SensorDTOValidator sensorDTOValidator) {
		this.sensorsService = sensorsService;
		this.modelMapper = modelMapper;
		this.sensorDTOValidator = sensorDTOValidator;
	}

	@PostMapping("/register")
	public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

		sensorDTOValidator.validate(sensorDTO, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidSensorException(ErrorMessage.getErrorMessage(bindingResult));
		}

		sensorsService.save(convertToSensor(sensorDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@ExceptionHandler
	private ResponseEntity<SensorErrorResponse> handleCreateException(NotValidSensorException ex) {

		SensorErrorResponse response = new SensorErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	private Sensor convertToSensor(SensorDTO sensorDTO) {

		return modelMapper.map(sensorDTO, Sensor.class);
	}
}