package com.hoshblok.SensorAPI.services.implementations;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hoshblok.SensorAPI.dto.auth.request.LoginRequest;
import com.hoshblok.SensorAPI.dto.auth.response.AuthResponse;
import com.hoshblok.SensorAPI.security.JWTUtil;
import com.hoshblok.SensorAPI.services.interfaces.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final JWTUtil jwtUtil;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthenticationServiceImpl(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Cookie getRefreshTokenCookieForLogin(LoginRequest request) {

		return jwtUtil.generateRefreshTokenCookie(request.getUsername(), request.getRole());
	}

	@Override
	public Cookie getRefreshTokenCookieForRefresh(String cookieWithToken) {

		Map<String, String> claims = jwtUtil.retrieveClaim(jwtUtil.getDecodedToken(cookieWithToken));

		return jwtUtil.generateRefreshTokenCookie(claims.get("username"), claims.get("role"));
	}

	@Override
	public AuthResponse getAuthResponseForLogin(LoginRequest request) {

		UsernamePasswordAuthenticationToken authRequestToken = new UsernamePasswordAuthenticationToken(request
			.getUsername(), request.getPassword());
		authRequestToken.setDetails(request.getRole());

		authenticationManager.authenticate(authRequestToken);

		if (request.getRole().equals("person")) {
			return new AuthResponse(jwtUtil.generateAccessToken(request.getUsername(), request.getRole()));
		}
		return new AuthResponse(jwtUtil.generateAccessToken(request.getUsername(), request.getRole()));
	}

	@Override
	public AuthResponse getAuthResponseForRefresh(String refreshToken) {

		DecodedJWT decodedToken = jwtUtil.getDecodedToken(refreshToken);

		String username = jwtUtil.retrieveClaim(decodedToken).get("username");
		String role = jwtUtil.retrieveClaim(decodedToken).get("role");

		if (role.equals("ROLE_PERSON")) {
			return new AuthResponse(jwtUtil.generateAccessToken(username, role));
		}
		return new AuthResponse(jwtUtil.generateAccessToken(username, role));
	}
}