package com.aubay.w2m.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aubay.w2m.dao.INaveDao;
import com.aubay.w2m.dto.NaveListResponseDto;
import com.aubay.w2m.model.Nave;
import com.aubay.w2m.service.impl.NaveServiceImpl;
import com.aubay.w2m.util.MockData;

@ExtendWith(MockitoExtension.class)
class NaveServiceTest {

	@Mock
	private INaveDao naveDao;

	@InjectMocks
	private NaveServiceImpl naveService;

	private Nave nave;

	@BeforeEach
	public void init() {
		nave = MockData.NAVE;
	}

	@Test
	void find() {
		when(naveDao.findAll(Mockito.any(Pageable.class))).thenReturn(Mockito.mock(Page.class));

		final NaveListResponseDto response = naveService.getNaves(Optional.of(MockData.EMPTY), MockData.PARAM_PAGE,
				MockData.PARAM_SIZE);

		Assertions.assertThat(response).isNotNull();
	}

	@Test
	void create() {
		when(naveDao.save(Mockito.any(Nave.class))).thenReturn(nave);

		final Nave nave = naveService.create(MockData.NAVE_DTO);

		Assertions.assertThat(nave).isNotNull();
		Assertions.assertThat(nave.getId()).isEqualTo(Long.valueOf(MockData.NAVE_ID));
	}

	@Test
	void update() throws Exception {
		when(naveDao.findById(Long.valueOf(MockData.NAVE_ID))).thenReturn(Optional.of(nave));
		when(naveDao.save(Mockito.any(Nave.class))).thenReturn(nave);

		final Nave nave = naveService.update(MockData.NAVE_ID, MockData.NAVE_DTO);

		Assertions.assertThat(nave).isNotNull();
		Assertions.assertThat(nave.getNombre()).isEqualTo(MockData.NAVE_NOMBRE);
	}

	@Test
	void delete() throws Exception {
		when(naveDao.findById(Long.valueOf(MockData.NAVE_ID))).thenReturn(Optional.of(nave));

		naveService.delete(MockData.NAVE_ID);

		assertAll(() -> naveService.delete(MockData.NAVE_ID));
	}

}
