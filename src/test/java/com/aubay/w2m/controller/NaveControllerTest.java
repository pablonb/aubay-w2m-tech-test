package com.aubay.w2m.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.aubay.w2m.dto.NaveListResponseDto;
import com.aubay.w2m.dto.NaveRequestDto;
import com.aubay.w2m.model.Nave;
import com.aubay.w2m.service.INaveService;
import com.aubay.w2m.util.MockData;
import com.aubay.w2m.util.W2MConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.CoreMatchers;

@WebMvcTest(controllers = NaveController.class)
@ExtendWith(MockitoExtension.class)
class NaveControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private INaveService naveService;

	@Autowired
	private ObjectMapper om;

	private Nave nave;
	private NaveRequestDto naveDto;
	private NaveListResponseDto naveListResponseDto;

	@BeforeEach
	public void init() {
		nave = MockData.NAVE;
		naveDto = MockData.NAVE_DTO;
		naveListResponseDto = MockData.NAVE_LIST_RESPONSE_DTO;
	}

	@Test
	void find() throws Exception {
		when(naveService.getNaves(Optional.of(MockData.NAVE_QUERY), MockData.PARAM_PAGE, MockData.PARAM_SIZE))
				.thenReturn(naveListResponseDto);

		mockMvc.perform(MockMvcRequestBuilders.get(W2MConstant.API_ENDPOINT)
				.queryParam(MockData.PARAM_QUERY, MockData.NAVE_QUERY).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath(MockData.EXPECTED_NAVES_SIZE,
						CoreMatchers.is(naveListResponseDto.getNaves().size())));
	}

	@Test
	void create() throws Exception {
		when(naveService.create(naveDto)).thenReturn(nave);

		mockMvc.perform(MockMvcRequestBuilders.post(W2MConstant.API_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(om.writeValueAsString(nave)))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath(MockData.EXPECTED_ID,
						CoreMatchers.is(Integer.valueOf(MockData.NAVE_ID))))
				.andExpect(MockMvcResultMatchers.jsonPath(MockData.EXPECTED_NOMBRE, CoreMatchers.is(nave.getNombre())));
	}

	@Test
	void update() throws Exception {
		when(naveService.update(MockData.NAVE_ID, naveDto)).thenReturn(nave);

		mockMvc.perform(MockMvcRequestBuilders.put(W2MConstant.API_ENDPOINT_ID, MockData.NAVE_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(nave))).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath(MockData.EXPECTED_ID,
						CoreMatchers.is(Integer.valueOf(MockData.NAVE_ID))))
				.andExpect(MockMvcResultMatchers.jsonPath(MockData.EXPECTED_NOMBRE, CoreMatchers.is(nave.getNombre())));
	}

	@Test
	void delete() throws Exception {
		doNothing().when(naveService).delete(MockData.NAVE_ID);

		mockMvc.perform(MockMvcRequestBuilders.delete(W2MConstant.API_ENDPOINT_ID, MockData.NAVE_ID)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}
