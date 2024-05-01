package com.aubay.w2m.exception;

import jakarta.persistence.EntityNotFoundException;

/**
 * Excepcion al no encontrar una nave
 */
public class NaveNotFoundException extends EntityNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7875286537634112094L;

	private static final String ERROR = "Nave no encontrada.";

	public NaveNotFoundException() {
		super(ERROR);
	}

	public NaveNotFoundException(final String msg, final Exception error) {
		super(msg, error);
	}

}
