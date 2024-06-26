package com.aubay.w2m.service;

import java.util.Optional;

import com.aubay.w2m.dto.NaveListResponseDto;
import com.aubay.w2m.dto.NaveRequestDto;
import com.aubay.w2m.exception.IdentificadorNoValidoException;
import com.aubay.w2m.model.Nave;

public interface INaveService {

	/**
	 * Recupera un listado de naves con la informacion del paginado
	 * 
	 * @param query Cadena a encontrar en los nombres de las naves
	 * @param page  Numero de pagina
	 * @param size  Numero de registros por pagina
	 * @return Listado de naves
	 */
	NaveListResponseDto getNaves(final Optional<String> query, final int page, final int size);

	/**
	 * Recupera la informacion de una nave
	 * 
	 * @param id Identificador del registro a recuperar
	 * @return Informacion de una nave
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a
	 *                                        Long
	 */
	Nave getNave(final String id) throws IdentificadorNoValidoException;

	/**
	 * Creacion de un registro
	 * 
	 * @param req Datos de entrada
	 * @return Registro creado
	 */
	Nave create(final NaveRequestDto req);

	/**
	 * Actualizacion de un registro
	 * 
	 * @param id  Identificador del registro a actualizar
	 * @param req Datos de entrada
	 * @return Registro actualizado
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a
	 *                                        Long
	 */
	Nave update(final String id, final NaveRequestDto req) throws IdentificadorNoValidoException;

	/**
	 * Elimina un registro
	 * 
	 * @param id Identificador del registro a eliminar
	 * @throws IdentificadorNoValidoException Error convirtiendo el identificador a
	 *                                        Long
	 */
	void delete(final String id) throws IdentificadorNoValidoException;

}
