package com.hoshblok.SensorAPI.exceptions;

public class NotValidMeasurementException extends RuntimeException {

	private static final long serialVersionUID = 69204353977415784L;

	public NotValidMeasurementException(String msg) {
		super(msg);
	}
}