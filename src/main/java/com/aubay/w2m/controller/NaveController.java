package com.aubay.w2m.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aubay.w2m.dto.NaveListResponseDto;
import com.aubay.w2m.dto.NaveRequestDto;
import com.aubay.w2m.exception.IdentificadorNoValidoException;
import com.aubay.w2m.model.Nave;
import com.aubay.w2m.service.INaveService;
import com.aubay.w2m.util.W2MConstant;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/naves")
@RequiredArgsConstructor
public class NaveController {

	private final INaveService naveService;

	/**
	 * Recupera un listado de naves con la informacion del paginado
	 * 
	 * @param query Cadena a encontrar en los nombres de las naves
	 * @param page  Numero de pagina
	 * @param size  Numero de registros por pagina
	 * @return Listado de naves
	 */
	@GetMapping
	public ResponseEntity<NaveListResponseDto> getNaves(@RequestParam final Optional<String> query,
			@RequestParam(defaultValue = W2MConstant.DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = W2MConstant.DEFAULT_PAGE_SIZE) int size) {

		return ResponseEntity.ok(naveService.getNaves(query, page, size));
	}

	/**
	 * Recupera la informacion de una nave
	 * 
	 * @param id Identificador del registro a recuperar
	 * @return Informacion de una nave
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a
	 *                                        Long
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Nave> getNave(@PathVariable final String id) throws IdentificadorNoValidoException {
		return ResponseEntity.ok(naveService.getNave(id));
	}

	/**
	 * Creacion de un registro
	 * 
	 * @param req Datos de entrada
	 * @return Registro creado
	 */
	@PostMapping
	public ResponseEntity<Nave> create(@RequestBody final NaveRequestDto req) {
		return new ResponseEntity<>(naveService.create(req), HttpStatus.CREATED);
	}

	/**
	 * Actualizacion de un registro
	 * 
	 * @param id  Identificador del registro a actualizar
	 * @param req Datos de entrada
	 * @return Registro actualizado
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a
	 *                                        Long
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Nave> update(@PathVariable final String id, @RequestBody final NaveRequestDto req)
			throws IdentificadorNoValidoException {

		return ResponseEntity.ok(naveService.update(id, req));
	}

	/**
	 * Elimina un registro
	 * 
	 * @param id Identificador del registro a eliminar
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a
	 *                                        Long
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable final String id) throws IdentificadorNoValidoException {
		naveService.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
