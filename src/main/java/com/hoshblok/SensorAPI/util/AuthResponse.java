package com.hoshblok.SensorAPI.util;

public class AuthResponse {
	
	private String jwt_token;
	
	public AuthResponse(String token) {
		this.jwt_token = token;
	}

	public String getJwt_token() {
		return jwt_token;
	}

	public void setJwt_token(String token) {
		this.jwt_token = token;
	}
}
