package com.hoshblok.SensorAPI.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SensorsControllerTest {

//	@Mock
//	private SensorsService sensorsService;
//
//	@Mock
//	private SensorDTOValidator sensorDTOValidator;
//
//	@Mock
//	private ModelMapper modelMapper;
//
//	@InjectMocks
//	private SensorsController sensorsController;
//
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	private void setup() {
//		mockMvc = MockMvcBuilders.standaloneSetup(sensorsController).build();
//	}
//
//	public void register_returnsStatus200_whenBindingResultHasNoErrors() throws Exception {
//
//		SensorDTO sensorDTO = new SensorDTO();
//		sensorDTO.setName("abс");
//		String json = new ObjectMapper().writeValueAsString(sensorDTO);
//
//		//@formatter:off 
//		mockMvc.perform(post("/sensors/register")
//		.contentType(MediaType.APPLICATION_JSON)
//        .content(json))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.errors").doesNotExist());
//		//@formatter:on
//	}
//
//	public void register_throwsException_whenBindingResultHasErrors() throws Exception {
//
//		SensorDTO sensorDTO = new SensorDTO();
//		String json = new ObjectMapper().writeValueAsString(sensorDTO);
//
//		//@formatter:off 
//		mockMvc.perform(post("/sensors/register")
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(json))
//		.andExpect(status().isBadRequest());
//		//@formatter:on
//	}
}