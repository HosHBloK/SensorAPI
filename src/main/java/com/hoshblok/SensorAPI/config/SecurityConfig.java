package com.hoshblok.SensorAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hoshblok.SensorAPI.services.PersonDetailsService;
import com.hoshblok.SensorAPI.services.SensorDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PersonDetailsService personDetailsService;
	private final SensorDetailsService sensorDetailsService;
	private final JWTFilter jwtFilter;

	@Autowired
	public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter,
		SensorDetailsService sensorDetailsService) {
		this.personDetailsService = personDetailsService;
		this.sensorDetailsService = sensorDetailsService;
		this.jwtFilter = jwtFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/login", "/auth/registration/sensor", "/auth/registration/person").permitAll()
		.anyRequest().hasAnyRole("USER", "SENSOR")
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//@formatter:on

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(personDetailsService).and().userDetailsService(sensorDetailsService);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
