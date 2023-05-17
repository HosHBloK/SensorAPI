package com.hoshblok.SensorAPI.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hoshblok.SensorAPI.security.JWTUtil;
import com.hoshblok.SensorAPI.services.PersonDetailsService;
import com.hoshblok.SensorAPI.services.SensorDetailsService;

@Component
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final PersonDetailsService personDetailsService;
	private final SensorDetailsService sensorDetailsService;

	@Autowired
	public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService,
		SensorDetailsService sensorDetailsService) {
		this.jwtUtil = jwtUtil;
		this.personDetailsService = personDetailsService;
		this.sensorDetailsService = sensorDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		FilterChain filterChain) throws ServletException, IOException {
		String authHeader = httpServletRequest.getHeader("Authorization");

		if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}

		try {
			String jwt = authHeader.substring(7);
			String username = jwtUtil.retrieveClaim(jwt).get("username");
			String role = jwtUtil.retrieveClaim(jwt).get("role");
			UserDetails userDetails;

			if (role.equals("ROLE_SENSOR") || role.equalsIgnoreCase("sensor")) {
				userDetails = sensorDetailsService.loadUserByUsername(username);
			} else {
				userDetails = personDetailsService.loadUserByUsername(username);
			}

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities());

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

			filterChain.doFilter(httpServletRequest, httpServletResponse);

		} catch (JWTVerificationException ex) {
			httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			httpServletResponse.getWriter().write("Invalid JWT token!");
		}
	}
}