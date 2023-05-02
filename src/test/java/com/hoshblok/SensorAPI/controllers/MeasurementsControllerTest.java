package com.hoshblok.SensorAPI.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoshblok.SensorAPI.dto.MeasurementDTO;
import com.hoshblok.SensorAPI.dto.SensorDTO;
import com.hoshblok.SensorAPI.models.Measurement;
import com.hoshblok.SensorAPI.models.Sensor;
import com.hoshblok.SensorAPI.services.MeasurmentsService;
import com.hoshblok.SensorAPI.util.MeasurementResponse;
import com.hoshblok.SensorAPI.validators.MeasurementDTOValidator;

@ExtendWith(MockitoExtension.class)
public class MeasurementsControllerTest {

	@Mock
	private MeasurmentsService measurmentsService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private MeasurementDTOValidator measurementDTOValidator;

	@InjectMocks
	private MeasurementsController measurementsController;

	private MockMvc mockMvc;

	@BeforeEach
	private void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(measurementsController).build();
	}

	
	public void getAll_renturnsAllDTOs() throws Exception {

		Measurement measurement = new Measurement(11.1f, false, new Sensor());
		MeasurementDTO measurementDTO = new MeasurementDTO(11.1f, false, new SensorDTO());
		
		MeasurementResponse expected = new MeasurementResponse(List.of(measurementDTO));
		Mockito.when(measurmentsService.findAll()).thenReturn(List.of(measurement));
		Mockito.when(modelMapper.map(measurement, MeasurementDTO.class)).thenReturn(measurementDTO);

		//@formatter:off 
		 MvcResult result = mockMvc.perform(get("/measurements"))
        .andExpect(status().isOk())
        .andReturn();
		 //@formatter:on
		 
		String responseBody = result.getResponse().getContentAsString();
		MeasurementResponse response = new ObjectMapper().readValue(responseBody, MeasurementResponse.class);
		assertEquals(expected.getMeasurements().get(0).getValue(), response.getMeasurements().get(0).getValue());
	}

	
	public void countRainyDays_returns1_when1RainyMeasurement() throws Exception {

		Measurement measurement = new Measurement();
		measurement.setRaining(true);
		Mockito.when(measurmentsService.findAll()).thenReturn(List.of(measurement));

		//@formatter:off 
		 MvcResult result = mockMvc.perform(get("/measurements/rainyDaysCount"))
        .andExpect(status().isOk())
        .andReturn();
		 //@formatter:on
		 
		assertEquals("1", result.getResponse().getContentAsString());
	}
	
	
	public void addMeasurement_returnsStatus200_whenBindingResultHasNoErrors() throws Exception {
		
		MeasurementDTO measurementDTO = new MeasurementDTO(11.1f, false, new SensorDTO());
		String json = new ObjectMapper().writeValueAsString(measurementDTO);

		//@formatter:off 
		mockMvc.perform(post("/measurements/add")
		.contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errors").doesNotExist());
		//@formatter:on
	}
	
	
	public void addMeasurement_throwsException_whenBindingResultHasErrors() throws Exception {

		MeasurementDTO measurementDTO = new MeasurementDTO();
		String json = new ObjectMapper().writeValueAsString(measurementDTO);

		//@formatter:off 
		mockMvc.perform(post("/measurements/add")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest());
		//@formatter:on
	}
}