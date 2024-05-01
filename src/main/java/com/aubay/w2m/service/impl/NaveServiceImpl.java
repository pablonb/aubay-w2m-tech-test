package com.aubay.w2m.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aubay.w2m.dao.INaveDao;
import com.aubay.w2m.dto.NaveListResponseDto;
import com.aubay.w2m.dto.NaveRequestDto;
import com.aubay.w2m.exception.NaveNotFoundException;
import com.aubay.w2m.model.Nave;
import com.aubay.w2m.service.INaveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaveServiceImpl implements INaveService {

	private final INaveDao naveDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NaveListResponseDto getNaves(final Optional<String> query, final int page, final int size) {
		final Pageable pageable = PageRequest.of(page, size);
		Page<Nave> navesPage;

		if (query.isPresent()) {
			navesPage = naveDao.findByNombreContains(query.get(), pageable);
		} else {
			navesPage = naveDao.findAll(pageable);
		}

		final NaveListResponseDto response = new NaveListResponseDto();

		navesPage.getContent().stream().forEach(nave -> response.getNaves().add(nave));

		response.setPagina(navesPage.getNumber());
		response.setPaginas(navesPage.getTotalPages());
		response.setTotal(navesPage.getNumberOfElements());

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Nave getNave(final String id) {
		return findNaveById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Nave create(final NaveRequestDto req) {
		final Nave nave = Nave.builder().nombre(req.getNombre()).build();
		return naveDao.save(nave);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Nave update(final String id, final NaveRequestDto req) {
		Nave nave = findNaveById(id);
		nave.setNombre(req.getNombre());

		return naveDao.save(nave);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final String id) {
		final Nave nave = findNaveById(id);

		naveDao.deleteById(nave.getId());
	}

	/**
	 * Recupera la informacion de una nave
	 * 
	 * @param id Identificador del registro a recuperar
	 * @return Informacion de una nave
	 */
	private Nave findNaveById(final String id) {
		return naveDao.findById(Long.valueOf(id)).orElseThrow(NaveNotFoundException::new);
	}

}
