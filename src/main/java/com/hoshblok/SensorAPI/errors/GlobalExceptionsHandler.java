package com.hoshblok.SensorAPI.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hoshblok.SensorAPI.exceptions.NotValidAuthenticationException;
import com.hoshblok.SensorAPI.exceptions.NotValidMeasurementException;
import com.hoshblok.SensorAPI.exceptions.NotValidPersonException;
import com.hoshblok.SensorAPI.exceptions.NotValidSensorException;
import com.hoshblok.SensorAPI.exceptions.SensorNotFoundException;

@RestControllerAdvice
public class GlobalExceptionsHandler {

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleLoginException(NotValidSensorException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleLoginException(BadCredentialsException ex) {
		
		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleCreateException(NotValidAuthenticationException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleCreateException(NotValidPersonException ex) {

		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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