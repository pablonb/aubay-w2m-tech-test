package com.aubay.w2m.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorDto {

	private String fecha;
	private HttpStatus estado;
	private String detalle;
	private List<String> errores;

	public ErrorDto(HttpStatus estado, String detalle, List<String> errores) {
		super();
		this.fecha = LocalDateTime.now().toString();
		this.estado = estado;
		this.detalle = detalle;
		this.errores = errores != null && !errores.isEmpty() ? errores : Arrays.asList(detalle);
	}

}
