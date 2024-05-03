package com.aubay.w2m.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aubay.w2m.dto.ErrorDto;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// Inicio: excepciones clase heredada

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		final StringBuilder sb = new StringBuilder();

		if (e.getSupportedHttpMethods() != null) {
			sb.append("El metodo ");
			sb.append(e.getMethod());
			sb.append(" no es valido para esta peticion. Los metodos validos son ");

			e.getSupportedHttpMethods().stream().forEach(metodo -> sb.append(metodo + " "));
		}

		return new ResponseEntity<>(
				buildError(HttpStatus.METHOD_NOT_ALLOWED, formateaMensajeError(e), Arrays.asList(sb.toString())),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		final StringBuilder sb = new StringBuilder();
		sb.append("El media type ");
		sb.append(e.getContentType());
		sb.append(" no es valido para esta peticion. Los media type validos son ");

		e.getSupportedMediaTypes().stream().forEach(metodo -> sb.append(metodo + " "));

		return new ResponseEntity<>(
				buildError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, formateaMensajeError(e), Arrays.asList(sb.toString())),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		return new ResponseEntity<>(buildError(HttpStatus.NOT_ACCEPTABLE, formateaMensajeError(e)),
				HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		return new ResponseEntity<>(buildError(HttpStatus.INTERNAL_SERVER_ERROR, formateaMensajeError(e)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		final String mensajeEspecifico = "Parte obligatoria no encontrada: " + e.getRequestPartName();

		return new ResponseEntity<>(
				buildError(HttpStatus.BAD_REQUEST, formateaMensajeError(e), Arrays.asList(mensajeEspecifico)),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		return new ResponseEntity<>(buildError(HttpStatus.BAD_REQUEST, formateaMensajeError(e)),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		final List<String> errores = new ArrayList<>();

		e.getBindingResult().getFieldErrors().stream()
				.forEach(error -> errores.add(error.getField() + ": " + error.getDefaultMessage()));
		e.getBindingResult().getGlobalErrors().stream()
				.forEach(error -> errores.add(error.getObjectName() + ": " + error.getDefaultMessage()));

		return new ResponseEntity<>(buildError(HttpStatus.UNPROCESSABLE_ENTITY, formateaMensajeError(e), errores),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		final String mensajeEspecifico = "Metodo " + e.getHttpMethod() + " no encontrado para la URL "
				+ e.getRequestURL();

		return new ResponseEntity<>(
				buildError(HttpStatus.BAD_REQUEST, formateaMensajeError(e), Arrays.asList(mensajeEspecifico)),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		final StringBuilder sb = new StringBuilder();
		sb.append("El tipo de la propiedad " + e.getPropertyName() + " no es el esperado.");

		if (e.getRequiredType() != null) {
			sb.append(" Debe ser del tipo " + e.getRequiredType().getName());
		}

		return new ResponseEntity<>(
				buildError(HttpStatus.BAD_REQUEST, formateaMensajeError(e), Arrays.asList(sb.toString())),
				HttpStatus.BAD_REQUEST);
	}

	// Fin: excepciones clase heredada

	// Inicio: excepciones personalizadas

	/**
	 * Captura excepciones a nivel general
	 * 
	 * @param ex      Excepcion producida
	 * @param request Informacion de la peticion
	 * @return Datos a devolver por el servicio
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDto> handle(Exception e, WebRequest request) {
		return new ResponseEntity<>(buildError(HttpStatus.INTERNAL_SERVER_ERROR, formateaMensajeError(e)),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * Captura excepciones sobre los resultados no encontrados en entidades
	 * 
	 * @param ex      Excepcion producida
	 * @param request Informacion de la peticion
	 * @return Datos a devolver por el servicio
	 * @throws Exception
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<ErrorDto> handleEntityNotFound(Exception e, WebRequest request) {
		return new ResponseEntity<>(buildError(HttpStatus.NOT_FOUND, formateaMensajeError(e)), HttpStatus.NOT_FOUND);
	}

	/**
	 * Captura excepciones sobre la validacion de identificadores de tipo Long
	 * 
	 * @param ex      Excepcion producida
	 * @param request Informacion de la peticion
	 * @return Datos a devolver por el servicio
	 * @throws Exception
	 */
	@ExceptionHandler(IdentificadorNoValidoException.class)
	public final ResponseEntity<ErrorDto> handleIdentificadorNoValido(Exception e, WebRequest request) {
		return new ResponseEntity<>(buildError(HttpStatus.BAD_REQUEST, formateaMensajeError(e)),
				HttpStatus.BAD_REQUEST);
	}

	// Fin: excepciones personalizadas

	/**
	 * Monta el objeto de error
	 * 
	 * @param estado  Codigo HTTP
	 * @param detalle Detalle del mensaje
	 * @return Objeto ocn los datos de la excepcion
	 */
	private ErrorDto buildError(final HttpStatus estado, final String detalle) {
		return new ErrorDto(estado, detalle, null);
	}

	/**
	 * Monta el objeto de error con los detalles especificos de cada error
	 * 
	 * @param estado  Codigo HTTP
	 * @param detalle Detalle del mensaje
	 * @param errores Errores especificos
	 * @return Objeto ocn los datos de la excepcion
	 */
	private ErrorDto buildError(final HttpStatus estado, final String detalle, final List<String> errores) {
		return new ErrorDto(estado, detalle, errores);
	}

	/**
	 * Formatea el mensaje original para quedarse con la parte que no informa del
	 * metodo Java
	 * 
	 * @param mensaje Mensaje original
	 * @return Mensaje formateado
	 */
	private String formateaMensajeError(final Exception e) {
		final String strBusqueda = ": ";
		String mensajeError = e.getLocalizedMessage();

		if (mensajeError == null) {
			mensajeError = e.getCause().getLocalizedMessage();
		}

		if (mensajeError != null && mensajeError.contains(": ")) {
			mensajeError = mensajeError.split(strBusqueda)[1];
		}

		return mensajeError;
	}

}
