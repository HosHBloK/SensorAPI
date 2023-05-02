package com.hoshblok.SensorAPI.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoshblok.SensorAPI.dto.AuthenticationDTO;
import com.hoshblok.SensorAPI.dto.PersonDTO;
import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.exceptions.NotValidPersonException;
import com.hoshblok.SensorAPI.exceptions.NotValidSensorException;
import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.security.JWTUtil;
import com.hoshblok.SensorAPI.services.PersonRegistrationService;
import com.hoshblok.SensorAPI.services.SensorRegistrationService;
import com.hoshblok.SensorAPI.util.AuthResponse;
import com.hoshblok.SensorAPI.util.ErrorMessage;
import com.hoshblok.SensorAPI.util.ErrorResponse;
import com.hoshblok.SensorAPI.util.SensorErrorResponse;
import com.hoshblok.SensorAPI.validators.PersonValidator;
import com.hoshblok.SensorAPI.validators.SensorDTOValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final PersonValidator personValidator;
	private final PersonRegistrationService personRegistrationService;
	private final JWTUtil jwtUtil;
	private final ModelMapper modelMapper;
	private final AuthenticationManager authenticationManager;
	private final SensorRegistrationService sensorRegistrationService;
	private final SensorDTOValidator sensorDTOValidator;

	@Autowired
	public AuthController(PersonValidator personValidator, PersonRegistrationService personRegistrationService, JWTUtil jwtUtil,
		ModelMapper modelMapper, AuthenticationManager authenticationManager, SensorRegistrationService sensorRegistrationService,
		SensorDTOValidator sensorDTOValidator) {
		this.personValidator = personValidator;
		this.personRegistrationService = personRegistrationService;
		this.jwtUtil = jwtUtil;
		this.modelMapper = modelMapper;
		this.authenticationManager = authenticationManager;
		this.sensorRegistrationService = sensorRegistrationService;
		this.sensorDTOValidator = sensorDTOValidator;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> performLogin(@RequestBody @Valid AuthenticationDTO authenticationDTO)
		throws BadCredentialsException {

		UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(authenticationDTO
			.getUsername(), authenticationDTO.getPassword());

		authenticationManager.authenticate(authInputToken);

		String token = jwtUtil.generateToken(authenticationDTO.getUsername(), authenticationDTO.getRole());

		AuthResponse response = new AuthResponse(token);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/registration/sensor")
	public ResponseEntity<AuthResponse> performRegistration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

		Sensor sensor = convertToSensor(sensorDTO);
		
		sensorDTOValidator.validate(sensor, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidSensorException(ErrorMessage.getErrorMessage(bindingResult));
		}

		sensorRegistrationService.register(sensor);
		
		String token = jwtUtil.generateToken(sensor.getUsername(), sensor.getRole());

		AuthResponse response = new AuthResponse(token);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/registration/person")
	public ResponseEntity<AuthResponse> performRegistration(@RequestBody @Valid PersonDTO personDTO,
		BindingResult bindingResult) {

		Person person = convertToPerson(personDTO);

		personValidator.validate(person, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidPersonException(ErrorMessage.getErrorMessage(bindingResult));
		}
		personRegistrationService.register(person);

		String token = jwtUtil.generateToken(person.getUsername(), person.getRole());

		AuthResponse response = new AuthResponse(token);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private Person convertToPerson(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Person.class);
	}

	private Sensor convertToSensor(SensorDTO sensorDTO) {

		return modelMapper.map(sensorDTO, Sensor.class);
	}

	@ExceptionHandler
	private ResponseEntity<SensorErrorResponse> handleCreateException(NotValidSensorException ex) {

		SensorErrorResponse response = new SensorErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleCreateException(NotValidPersonException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleLoginException(BadCredentialsException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
