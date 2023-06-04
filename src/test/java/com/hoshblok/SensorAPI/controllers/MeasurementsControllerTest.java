package com.hoshblok.SensorAPI.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshblok.SensorAPI.dto.measurement.request.AddMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.request.GetMeasurementRequest;
import com.hoshblok.SensorAPI.dto.measurement.response.MeasurementResponse;
import com.hoshblok.SensorAPI.dto.measurement.response.RainyDaysResponse;
import com.hoshblok.SensorAPI.errors.GlobalExceptionsHandler;
import com.hoshblok.SensorAPI.services.interfaces.MeasurementsService;
import com.hoshblok.SensorAPI.services.interfaces.SensorsService;
import com.hoshblok.SensorAPI.validators.AddMeasurementRequestValidator;
import com.hoshblok.SensorAPI.validators.GetMeasurementRequestValidator;

@ExtendWith(MockitoExtension.class)
public class MeasurementsControllerTest {

	@Mock
	private MeasurementsService measurmentsService;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private BindingResult bindingResult;
	@Mock
	private SensorsService sensorsService;
	@Mock
	private AddMeasurementRequestValidator addMeasurementRequestValidator;
	@Mock
	private GetMeasurementRequestValidator getMeasurementRequestValidator;

	@InjectMocks
	private MeasurementsController measurementsController;

	private MockMvc mockMvc;

	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(measurementsController).setControllerAdvice(
			GlobalExceptionsHandler.class).build();
	}

	@Test
	public void getMeasurementsOfSensor_renturnsMeasurementResponse_whenBindingResultHasNoErrors() throws Exception {

		GetMeasurementRequest getMeasurementRequest = new GetMeasurementRequest();
		getMeasurementRequest.setUsername("abc");

		String json = new ObjectMapper().writeValueAsString(getMeasurementRequest);

		MeasurementResponse expectedResponse = new MeasurementResponse("11.1", "false", "abc");

		Mockito.when(measurmentsService.getMeasurements(any())).thenReturn(List.of(expectedResponse));

		//@formatter:off 
		 MvcResult result = mockMvc.perform(post("/measurements/get")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andReturn();
		 //@formatter:on

		String responseBody = result.getResponse().getContentAsString();
		List<MeasurementResponse> actualResponse = new ObjectMapper().readValue(responseBody,
			new TypeReference<List<MeasurementResponse>>() {
			});
		assertEquals(List.of(expectedResponse).get(0).toString(), actualResponse.get(0).toString());
	}

	@Test
	public void getMeasurementsOfSensor_throwsException_whenBindingResultHasErrors() throws Exception {

		GetMeasurementRequest getMeasurementRequest = new GetMeasurementRequest();

		String json = new ObjectMapper().writeValueAsString(getMeasurementRequest);

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

		GetMeasurementRequest getMeasurementRequest = new GetMeasurementRequest();
		getMeasurementRequest.setUsername("abc");

		String json = new ObjectMapper().writeValueAsString(getMeasurementRequest);

		RainyDaysResponse expectedResponse = new RainyDaysResponse(1);

		Mockito.when(measurmentsService.getRainyDaysCount(any())).thenReturn(expectedResponse);

		//@formatter:off 
		 MvcResult result = mockMvc.perform(post("/measurements/rainy_days_count")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andReturn();
		 //@formatter:on

		String responseBody = result.getResponse().getContentAsString();
		RainyDaysResponse Actualresponse = new ObjectMapper().readValue(responseBody, RainyDaysResponse.class);
		assertEquals(expectedResponse.getRainy_days_count(), Actualresponse.getRainy_days_count());
	}

	@Test
	public void countRainyDaysOfSensor_throwsException_whenBindingResultHasErrors() throws Exception {

		GetMeasurementRequest getMeasurementRequest = new GetMeasurementRequest();

		String json = new ObjectMapper().writeValueAsString(getMeasurementRequest);

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

		AddMeasurementRequest addMeasurementRequest = new AddMeasurementRequest("11.1", "false", "abc");

		String json = new ObjectMapper().writeValueAsString(addMeasurementRequest);

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

		AddMeasurementRequest addMeasurementRequest = new AddMeasurementRequest();

		String json = new ObjectMapper().writeValueAsString(addMeasurementRequest);

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