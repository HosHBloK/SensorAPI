package com.hoshblok.SensorAPI.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoshblok.SensorAPI.dto.auth.request.LoginRequest;
import com.hoshblok.SensorAPI.dto.auth.response.AuthResponse;
import com.hoshblok.SensorAPI.dto.person.request.PersonRegistrationRequest;
import com.hoshblok.SensorAPI.dto.sensor.request.SensorRegistrationRequest;
import com.hoshblok.SensorAPI.errors.ErrorMessage;
import com.hoshblok.SensorAPI.exceptions.NotValidAuthenticationException;
import com.hoshblok.SensorAPI.exceptions.NotValidPersonException;
import com.hoshblok.SensorAPI.exceptions.NotValidSensorException;
import com.hoshblok.SensorAPI.security.JWTUtil;
import com.hoshblok.SensorAPI.services.interfaces.AuthenticationService;
import com.hoshblok.SensorAPI.services.interfaces.PersonRegistrationService;
import com.hoshblok.SensorAPI.services.interfaces.SensorRegistrationService;
import com.hoshblok.SensorAPI.validators.LoginRequestValidator;
import com.hoshblok.SensorAPI.validators.PersonRegistrationRequestValidator;
import com.hoshblok.SensorAPI.validators.SensorRegistrationRequestValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final LoginRequestValidator loginvalidator;
	private final PersonRegistrationRequestValidator personRegistrationValidator;
	private final SensorRegistrationRequestValidator sensorRegistrationValidator;
	private final SensorRegistrationService sensorRegistrationService;
	private final PersonRegistrationService personRegistrationService;
	private final AuthenticationService authenticationService;
	private final JWTUtil jwtUtil;

	@Autowired
	public AuthController(LoginRequestValidator personLoginvalidator,
		PersonRegistrationRequestValidator personRegistrationValidator,
		SensorRegistrationRequestValidator sensorRegistrationValidator,
		SensorRegistrationService sensorRegistrationService, PersonRegistrationService personRegistrationService,
		AuthenticationService authenticationService, JWTUtil jwtUtil) {
		this.loginvalidator = personLoginvalidator;
		this.personRegistrationValidator = personRegistrationValidator;
		this.sensorRegistrationValidator = sensorRegistrationValidator;
		this.sensorRegistrationService = sensorRegistrationService;
		this.personRegistrationService = personRegistrationService;
		this.authenticationService = authenticationService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> performPersonLogin(@RequestBody @Valid LoginRequest request,
		BindingResult bindingResult, HttpServletResponse response) {

		loginvalidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidAuthenticationException(ErrorMessage.getErrorMessage(bindingResult));
		}

		response.addCookie(authenticationService.getRefreshTokenCookieForLogin(request));

		return new ResponseEntity<>(authenticationService.getAuthResponseForLogin(request), HttpStatus.OK);
	}

	@PostMapping("/refresh_tokens")
	public ResponseEntity<AuthResponse> refreshTokens(
		@CookieValue(value = "refreshToken", required = false) String cookieValue, HttpServletResponse response) {

		if (cookieValue == null) {
			throw new NotValidAuthenticationException("Refresh token cookie is not found. Perfom login!");
		}

		if (!jwtUtil.verifyToken(cookieValue)) {
			throw new NotValidAuthenticationException("Refresh token cookie is not valid. Perfom login!");
		}

		response.addCookie(authenticationService.getRefreshTokenCookieForRefresh(cookieValue));

		return new ResponseEntity<>(authenticationService.getAuthResponseForRefresh(cookieValue), HttpStatus.OK);
	}

	@PostMapping("/registration/sensor")
	public ResponseEntity<HttpStatus> performSensorRegistration(@RequestBody @Valid SensorRegistrationRequest request,
		BindingResult bindingResult) {

		sensorRegistrationValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidSensorException(ErrorMessage.getErrorMessage(bindingResult));
		}

		sensorRegistrationService.register(request);

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PostMapping("/registration/person")
	public ResponseEntity<HttpStatus> performPersonRegistration(@RequestBody @Valid PersonRegistrationRequest request,
		BindingResult bindingResult) {

		personRegistrationValidator.validate(request, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new NotValidPersonException(ErrorMessage.getErrorMessage(bindingResult));
		}
		personRegistrationService.register(request);

		return ResponseEntity.ok(HttpStatus.OK);
	}
}