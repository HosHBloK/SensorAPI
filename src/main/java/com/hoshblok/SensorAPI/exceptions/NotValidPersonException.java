package com.hoshblok.SensorAPI.exceptions;

public class NotValidPersonException extends RuntimeException {

	private static final long serialVersionUID = 8574313964155223459L;

	public NotValidPersonException(String msg) {
		super(msg);
	}
}