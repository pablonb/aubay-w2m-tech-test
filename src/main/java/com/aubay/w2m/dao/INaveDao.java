package com.aubay.w2m.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aubay.w2m.model.Nave;

@Repository
public interface INaveDao extends JpaRepository<Nave, Long> {

	/**
	 * Recupera un listado de naves que contengan en el nombre el valor pasado como
	 * parametro con paginado
	 * 
	 * @param query    Cadena a encontrar en los nombres de las naves
	 * @param pageable Informacion del paginado
	 * @return Listado de naves que coincidan con las busquedas y el paginado
	 *         indicado
	 */
	Page<Nave> findByNombreContains(final String query, final Pageable pageable);

}
