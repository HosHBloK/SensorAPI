package com.hoshblok.SensorAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hoshblok.SensorAPI.services.implementations.PersonDetailsService;
import com.hoshblok.SensorAPI.services.implementations.SensorDetailsService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final PersonDetailsService personDetailsService;
	private final SensorDetailsService sensorDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public CustomAuthenticationProvider(PersonDetailsService personDetailsService,
		SensorDetailsService sensorDetailsService, PasswordEncoder passwordEncoder) {
		this.personDetailsService = personDetailsService;
		this.sensorDetailsService = sensorDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String role = (String) authentication.getDetails();

		UserDetails userDetails;

		if (role.equals("person")) {
			userDetails = personDetailsService.loadUserByUsername(username);
		}
		else {
			userDetails = sensorDetailsService.loadUserByUsername(username);
		}
		if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}