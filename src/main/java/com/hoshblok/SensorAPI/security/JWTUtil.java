package com.hoshblok.SensorAPI.security;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(String username, String role) {

		Date expirationDate = Date.from(ZonedDateTime.now().plusHours(6).toInstant());
		//@formatter:off
		return JWT.create()
			.withSubject("User details")
			.withClaim("username", username)
			.withClaim("role", role)
			.withIssuedAt(new Date())
			.withIssuer("WeatherSensorAPI")
			.withExpiresAt(expirationDate)
			.sign(Algorithm.HMAC256(secret));
		//@formatter:on

	}

	public Map<String, String> retrieveClaim(String token) {

		DecodedJWT jwt = validateToken(token);
		Map<String, String> claims = new HashMap<>();
		claims.put("username", jwt.getClaim("username").asString());
		claims.put("role", jwt.getClaim("role").asString());

		return claims;
	}

	private DecodedJWT validateToken(String token) throws JWTVerificationException {
		//@formatter:off
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
		.withSubject("User details")
		.withIssuer("WeatherSensorAPI")
		.build();
		//@formatter:on
		return verifier.verify(token);
	}
}
