package com.hoshblok.SensorAPI.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshblok.SensorAPI.dto.AuthenticationDTO;
import com.hoshblok.SensorAPI.dto.PersonDTO;
import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.models.Person;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.security.JWTUtil;
import com.hoshblok.SensorAPI.services.PeopleService;
import com.hoshblok.SensorAPI.services.PersonRegistrationService;
import com.hoshblok.SensorAPI.services.SensorRegistrationService;
import com.hoshblok.SensorAPI.services.SensorsService;
import com.hoshblok.SensorAPI.validators.AuthenticationDTOValidator;
import com.hoshblok.SensorAPI.validators.PersonValidator;
import com.hoshblok.SensorAPI.validators.SensorDTOValidator;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@Mock
	private PersonValidator personValidator;
	@Mock
	private PersonRegistrationService personRegistrationService;
	@Mock
	private JWTUtil jwtUtil;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private SensorRegistrationService sensorRegistrationService;
	@Mock
	private SensorDTOValidator sensorDTOValidator;
	@Mock
	private AuthenticationDTOValidator authenticationDTOValidator;
	@Mock
	private PeopleService peopleService;
	@Mock
	private SensorsService sensorsService;

	@InjectMocks
	private AuthController authController;

	private MockMvc mockMvc;

	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	@Test
	public void performSensorRegistration_returnsTokenAndStatus200_whenBindingResultHasNoErrors() throws Exception {

		SensorDTO sensorDTO = new SensorDTO();
		sensorDTO.setUsername("abc");
		sensorDTO.setPassword("abc");

		String json = new ObjectMapper().writeValueAsString(sensorDTO);
		Mockito.when(sensorsService.convertToSensor(any(SensorDTO.class))).thenReturn(new Sensor());
		Mockito.when(jwtUtil.generateToken(null, null)).thenReturn("abc");

		//@formatter:off 
		mockMvc.perform(post("/auth/registration/sensor")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.jwt_token").exists())
        .andExpect(jsonPath("$.jwt_token").value("abc"))
        .andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void performSensorRegistration_throwsException_whenBindingResultHasErrors() throws Exception {

		SensorDTO sensorDTO = new SensorDTO();
		String json = new ObjectMapper().writeValueAsString(sensorDTO);

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
	public void performPersonRegistration_returnsTokenAndStatus200_whenBindingResultHasNoErrors() throws Exception {

		PersonDTO personDTO = new PersonDTO();
		personDTO.setUsername("abc");
		personDTO.setPassword("abc");

		String json = new ObjectMapper().writeValueAsString(personDTO);
		Mockito.when(peopleService.convertToPerson(any(PersonDTO.class))).thenReturn(new Person());
		Mockito.when(jwtUtil.generateToken(null, null)).thenReturn("abc");

		//@formatter:off 
		mockMvc.perform(post("/auth/registration/person")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.jwt_token").exists())
		.andExpect(jsonPath("$.jwt_token").value("abc"))
		.andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}
	
	@Test
	public void performPersonRegistration_throwsException_whenBindingResultHasErrors() throws Exception {

		PersonDTO personDTO = new PersonDTO();
		String json = new ObjectMapper().writeValueAsString(personDTO);

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
	public void refreshToken_returnsTokenAndStatus200_whenBindingResultHasNoErrors() throws Exception {

		AuthenticationDTO authenticationDTO = new AuthenticationDTO();
		authenticationDTO.setUsername("abc");
		authenticationDTO.setPassword("abc");
		authenticationDTO.setRole("sensor");

		String json = new ObjectMapper().writeValueAsString(authenticationDTO);
		Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
		Mockito.when(jwtUtil.generateToken(authenticationDTO.getUsername(), authenticationDTO.getRole())).thenReturn("abc");

		//@formatter:off 
		mockMvc.perform(post("/auth/refresh_token")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.jwt_token").exists())
		.andExpect(jsonPath("$.jwt_token").value("abc"))
		.andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}
	
	@Test
	public void refreshToken_throwsException_whenBindingResultHasErrors() throws Exception {
		
		AuthenticationDTO authenticationDTO = new AuthenticationDTO();
		
		String json = new ObjectMapper().writeValueAsString(authenticationDTO);
		
		//@formatter:off 
		mockMvc.perform(post("/auth/refresh_token")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}
}
