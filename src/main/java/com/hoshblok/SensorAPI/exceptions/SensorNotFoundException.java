package com.hoshblok.SensorAPI.exceptions;

public class SensorNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -3993919458421407952L;

	public SensorNotFoundException(String msg) {
		super(msg);
	}
}