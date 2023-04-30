package com.hoshblok.SensorAPI.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.services.SensorsService;
import com.hoshblok.SensorAPI.validators.SensorDTOValidator;

@ExtendWith(MockitoExtension.class)
public class SensorsControllerTest {

	@Mock
	private SensorsService sensorsService;

	@Mock
	private SensorDTOValidator sensorDTOValidator;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private SensorsController sensorsController;

	private MockMvc mockMvc;

	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(sensorsController).build();
	}

	@Test
	public void register_returnsStatus200_whenBindingResultHasNoErrors() throws Exception {

		SensorDTO sensorDTO = new SensorDTO();
		sensorDTO.setName("abс");
		String json = new ObjectMapper().writeValueAsString(sensorDTO);

		//@formatter:off 
		mockMvc.perform(post("/sensors/register")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void register_throwsException_whenBindingResultHasErrors() throws Exception {

		SensorDTO sensorDTO = new SensorDTO();
		String json = new ObjectMapper().writeValueAsString(sensorDTO);

		//@formatter:off 
		mockMvc.perform(post("/sensors/register")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest());
		//@formatter:on
	}
}