package com.hoshblok.SensorAPI.exceptions;

public class PersonNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -1649935168249317779L;

	public PersonNotFoundException(String msg) {
		super(msg);
	}
}