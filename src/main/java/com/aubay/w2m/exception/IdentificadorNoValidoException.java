package com.aubay.w2m.exception;

/**
 * Excepcion al no poder convertir un identificador a Long
 */
public class IdentificadorNoValidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7875286537634112094L;

	private static final String ERROR = "Identificador no valido.";

	public IdentificadorNoValidoException() {
		super(ERROR);
	}

	public IdentificadorNoValidoException(final String msg, final Exception error) {
		super(msg, error);
	}

}
