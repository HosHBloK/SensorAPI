package com.hoshblok.SensorAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hoshblok.SensorAPI.errors.ExceptionsHandlerFilter;
import com.hoshblok.SensorAPI.security.CustomAuthenticationProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomAuthenticationProvider authenticationProvider;
	private final JWTFilter jwtFilter;
	private final ExceptionsHandlerFilter exFilter;

	@Autowired
	public SecurityConfig(JWTFilter jwtFilter, ExceptionsHandlerFilter exFilter, @Lazy CustomAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
		this.jwtFilter = jwtFilter;
		this.exFilter = exFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.antMatchers("/auth/login", "/auth/registration/**", "/auth/refresh_tokens").permitAll()
		.antMatchers("/measurements/add").hasRole("SENSOR")
		.antMatchers("/measurements/get", "/measurements/rainy_days_count").hasRole("PERSON")
		.anyRequest().hasAnyRole("PERSON", "SENSOR")
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//@formatter:on

		http.addFilterBefore(exFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(authenticationProvider);
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