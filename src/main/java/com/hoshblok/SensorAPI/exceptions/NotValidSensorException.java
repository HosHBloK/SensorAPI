package com.hoshblok.SensorAPI.exceptions;

public class NotValidSensorException extends RuntimeException{
	
	private static final long serialVersionUID = -1618041231025564982L;

	public NotValidSensorException(String msg) {
		super(msg);
	}
}