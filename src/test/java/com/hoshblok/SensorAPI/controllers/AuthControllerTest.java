package com.hoshblok.SensorAPI.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshblok.SensorAPI.dto.auth.request.LoginRequest;
import com.hoshblok.SensorAPI.dto.auth.response.AuthResponse;
import com.hoshblok.SensorAPI.dto.person.request.PersonRegistrationRequest;
import com.hoshblok.SensorAPI.dto.sensor.request.SensorRegistrationRequest;
import com.hoshblok.SensorAPI.errors.GlobalExceptionsHandler;
import com.hoshblok.SensorAPI.security.JWTUtil;
import com.hoshblok.SensorAPI.services.interfaces.AuthenticationService;
import com.hoshblok.SensorAPI.services.interfaces.PersonRegistrationService;
import com.hoshblok.SensorAPI.services.interfaces.SensorRegistrationService;
import com.hoshblok.SensorAPI.validators.LoginRequestValidator;
import com.hoshblok.SensorAPI.validators.PersonRegistrationRequestValidator;
import com.hoshblok.SensorAPI.validators.SensorRegistrationRequestValidator;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@Mock
	private SensorRegistrationRequestValidator sensorRegistrationRequestValidator;
	@Mock
	private PersonRegistrationRequestValidator personRegistrationRequestValidator;
	@Mock
	private LoginRequestValidator loginRequestValidator; 
	@Mock
	private SensorRegistrationService sensorRegistrationService;
	@Mock
	private PersonRegistrationService personRegistrationService;
	@Mock
	private AuthenticationService authenticationService;
	@Mock
	private JWTUtil jwtUtil;

	@InjectMocks
	private AuthController authController;

	private MockMvc mockMvc;

	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(GlobalExceptionsHandler.class)
			.build();
	}

	@Test
	public void performSensorRegistration_returnsStatus200_whenBindingResultHasNoErrors() throws Exception {

		SensorRegistrationRequest request = new SensorRegistrationRequest();
		request.setUsername("abc");
		request.setPassword("abc");

		String json = new ObjectMapper().writeValueAsString(request);

		//@formatter:off 
		mockMvc.perform(post("/auth/registration/sensor")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void performSensorRegistration_throwsException_whenBindingResultHasErrors() throws Exception {

		SensorRegistrationRequest request = new SensorRegistrationRequest();

		String json = new ObjectMapper().writeValueAsString(request);

		//@formatter:off 
		mockMvc.perform(post("/auth/registration/sensor")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}

	@Test
	public void performPersonRegistration_returnsStatus200_whenBindingResultHasNoErrors() throws Exception {

		PersonRegistrationRequest request = new PersonRegistrationRequest();
		request.setUsername("abc");
		request.setPassword("abc");

		String json = new ObjectMapper().writeValueAsString(request);

		//@formatter:off 
		mockMvc.perform(post("/auth/registration/person")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void performPersonRegistration_throwsException_whenBindingResultHasErrors() throws Exception {

		PersonRegistrationRequest request = new PersonRegistrationRequest();

		String json = new ObjectMapper().writeValueAsString(request);

		//@formatter:off 
		mockMvc.perform(post("/auth/registration/person")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}

	@Test
	public void performSensorLogin_returnsTokensAndStatus200_whenBindingResultHasNoErrors() throws Exception {

		LoginRequest request = new LoginRequest("abc", "abc", "sensor");

		String json = new ObjectMapper().writeValueAsString(request);

		Cookie cookie = new Cookie("refreshToken", "123");
		Mockito.when(authenticationService.getRefreshTokenCookieForLogin(any())).thenReturn(cookie);

		Mockito.when(authenticationService.getAuthResponseForLogin(any())).thenReturn(new AuthResponse("jwt"));

		//@formatter:off 
		mockMvc.perform(post("/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.jwt_access_token").exists())
		.andExpect(jsonPath("$.jwt_access_token").value("jwt"))
		.andExpect(MockMvcResultMatchers.cookie().value("refreshToken", "123"))
		.andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void performPersonLogin_returnsTokensAndStatus200_whenBindingResultHasNoErrors() throws Exception {

		LoginRequest request = new LoginRequest("abc", "abc", "person");

		String json = new ObjectMapper().writeValueAsString(request);

		Cookie cookie = new Cookie("refreshToken", "123");
		Mockito.when(authenticationService.getRefreshTokenCookieForLogin(any())).thenReturn(cookie);

		Mockito.when(authenticationService.getAuthResponseForLogin(any())).thenReturn(new AuthResponse("jwt"));

		//@formatter:off 
		mockMvc.perform(post("/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.jwt_access_token").exists())
		.andExpect(jsonPath("$.jwt_access_token").value("jwt"))
		.andExpect(MockMvcResultMatchers.cookie().value("refreshToken", "123"))
		.andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void performLogin_returnsErrorResponse_whenBindingResultHasErrors() throws Exception {

		LoginRequest request = new LoginRequest();

		String json = new ObjectMapper().writeValueAsString(request);

		//@formatter:off 
		mockMvc.perform(post("/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}

	@Test
	public void refreshTokens_returnsTokensAndStatus200_whenBindingResultHasNoErrors() throws Exception {

		Cookie cookie = new Cookie("refreshToken", "123");

		Mockito.when(jwtUtil.verifyToken(any())).thenReturn(true);

		Mockito.when(authenticationService.getRefreshTokenCookieForRefresh(any())).thenReturn(cookie);

		Mockito.when(authenticationService.getAuthResponseForRefresh(any())).thenReturn(new AuthResponse("jwt"));

		//@formatter:off 
		mockMvc.perform(post("/auth/refresh_tokens")
			.cookie(cookie))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.jwt_access_token").exists())
		.andExpect(jsonPath("$.jwt_access_token").value("jwt"))
		.andExpect(MockMvcResultMatchers.cookie().value("refreshToken", "123"))
		.andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void refreshTokens_returnsErrorResponse_whenErrors() throws Exception {

		//@formatter:off 
		mockMvc.perform(post("/auth/refresh_tokens"))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}
}