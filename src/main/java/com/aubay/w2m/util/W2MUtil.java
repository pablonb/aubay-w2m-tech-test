package com.aubay.w2m.util;

import com.aubay.w2m.exception.IdentificadorNoValidoException;

public class W2MUtil {

	/**
	 * Valida que el identificador pasado como parametro sea numerico y lo convierte
	 * 
	 * @param id Identificador
	 * @return Identificador convertido
	 * @throws IdentificadorNoValidoException 
	 */
	public static Long validarID(final String id) throws IdentificadorNoValidoException {
		Long idAux;
		try {
			idAux = Long.valueOf(id);
		} catch (NullPointerException | NumberFormatException e) {
			throw new IdentificadorNoValidoException();
		}

		return idAux;
	}

}
