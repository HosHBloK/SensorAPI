package com.hoshblok.SensorAPI.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshblok.SensorAPI.dto.MeasurementDTO;
import com.hoshblok.SensorAPI.models.Measurement;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.services.MeasurmentsService;
import com.hoshblok.SensorAPI.services.SensorsService;
import com.hoshblok.SensorAPI.util.MeasurementRequest;
import com.hoshblok.SensorAPI.util.MeasurementResponse;
import com.hoshblok.SensorAPI.validators.MeasurementDTOValidator;
import com.hoshblok.SensorAPI.validators.MeasurementRequestValidator;

@ExtendWith(MockitoExtension.class)
public class MeasurementsControllerTest {

	@Mock
	private MeasurmentsService measurmentsService;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private SensorsService sensorsService;
	@Mock
	private MeasurementRequestValidator measurementRequestValidator;
	@Mock
	private MeasurementDTOValidator measurementDTOValidator;

	@InjectMocks
	private MeasurementsController measurementsController;

	private MockMvc mockMvc;

	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(measurementsController).build();
	}

	@Test
	public void getMeasurementsOfSensor_renturnsMeasurementResponse_whenBindingResultHasNoErrors() throws Exception {

		MeasurementRequest measurementRequest = new MeasurementRequest();
		measurementRequest.setUsername("abc");

		String json = new ObjectMapper().writeValueAsString(measurementRequest);

		Measurement measurement = new Measurement(11.1f, false, new Sensor());
		MeasurementDTO measurementDTO = new MeasurementDTO("11.1", "false", "null");
		MeasurementResponse expected = new MeasurementResponse(List.of(measurementDTO));

		Mockito.when(measurmentsService.findAll(anyString())).thenReturn(List.of(measurement));
		Mockito.when(measurmentsService.convertToMeasurementDTO(any(Measurement.class))).thenReturn(measurementDTO);

		//@formatter:off 
		 MvcResult result = mockMvc.perform(post("/measurements/get")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andReturn();
		 //@formatter:on

		String responseBody = result.getResponse().getContentAsString();
		MeasurementResponse response = new ObjectMapper().readValue(responseBody, MeasurementResponse.class);
		assertEquals(expected.getMeasurements().get(0).getValue(), response.getMeasurements().get(0).getValue());
	}

	@Test
	public void getMeasurementsOfSensor_throwsException_whenBindingResultHasErrors() throws Exception {

		MeasurementRequest measurementRequest = new MeasurementRequest();

		String json = new ObjectMapper().writeValueAsString(measurementRequest);

		//@formatter:off 
		mockMvc.perform(post("/measurements/get")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on

	}

	@Test
	public void countRainyDaysOfSensor_returns1_when1RainyMeasurementAndBindingResultHasNoErrors() throws Exception {
		
		MeasurementRequest measurementRequest = new MeasurementRequest();
		measurementRequest.setUsername("abc");

		String json = new ObjectMapper().writeValueAsString(measurementRequest);

		Measurement measurement = new Measurement();
		measurement.setRaining(true);
		Mockito.when(measurmentsService.findAll(anyString())).thenReturn(List.of(measurement));

		//@formatter:off 
		 MvcResult result = mockMvc.perform(post("/measurements/rainy_days_count")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andReturn();
		 //@formatter:on

		assertEquals("1", result.getResponse().getContentAsString());
	}
	
	@Test
	public void countRainyDaysOfSensor_throwsException_whenBindingResultHasErrors() throws Exception {
		
		MeasurementRequest measurementRequest = new MeasurementRequest();
		
		String json = new ObjectMapper().writeValueAsString(measurementRequest);
		
		//@formatter:off 
		mockMvc.perform(post("/measurements/rainy_days_count")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}

	@Test
	public void addMeasurement_returnsStatus200_whenBindingResultHasNoErrors() throws Exception {

		MeasurementDTO measurementDTO = new MeasurementDTO("11.1", "false", "abc");
		
		String json = new ObjectMapper().writeValueAsString(measurementDTO);
		
		Mockito.when(measurmentsService.convertToMeasurement(any(MeasurementDTO.class))).thenReturn(new Measurement());
	
		//@formatter:off 
		mockMvc.perform(post("/measurements/add")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}

	@Test
	public void addMeasurement_throwsException_whenBindingResultHasErrors() throws Exception {

		MeasurementDTO measurementDTO = new MeasurementDTO();
		String json = new ObjectMapper().writeValueAsString(measurementDTO);

		//@formatter:off 
		mockMvc.perform(post("/measurements/add")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").exists())
		.andExpect(jsonPath("$.timestamp").exists());
		//@formatter:on
	}
}