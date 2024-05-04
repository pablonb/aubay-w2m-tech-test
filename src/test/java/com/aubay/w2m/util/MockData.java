package com.aubay.w2m.util;

import java.util.ArrayList;
import java.util.List;

import com.aubay.w2m.dto.NaveListResponseDto;
import com.aubay.w2m.dto.NaveRequestDto;
import com.aubay.w2m.model.Nave;

public class MockData {

	public static final String NAVE_ID = "1";
	public static final String NAVE_NOMBRE = "X-WING BLACK ONE";
	public static final String NAVE_QUERY = "WING";

	public static final int PARAM_PAGE = 0;
	public static final int PARAM_SIZE = 5;
	public static final String PARAM_QUERY = "query";

	public static final int RESPONSE_PAGINA = 0;
	public static final int RESPONSE_PAGINAS = 2;
	public static final int RESPONSE_TOTAL = 10;

	public static final String EXPECTED_ID = "$.id";
	public static final String EXPECTED_NOMBRE = "$.nombre";

	public static final Nave NAVE = new Nave(Long.valueOf(NAVE_ID), NAVE_NOMBRE);
	public static final NaveRequestDto NAVE_DTO = new NaveRequestDto(MockData.NAVE_NOMBRE);
	public static final NaveListResponseDto NAVE_LIST_RESPONSE_DTO = initNaveListDto();

	/**
	 * Inicializacion constante NAVE_LIST_RESPONSE_DTO
	 * 
	 * @return Constante inicializada
	 */
	private static NaveListResponseDto initNaveListDto() {
		final List<Nave> naves = new ArrayList<>();
		naves.add(NAVE);

		final NaveListResponseDto response = new NaveListResponseDto();
		response.setNaves(naves);
		response.setPagina(RESPONSE_PAGINA);
		response.setPaginas(RESPONSE_PAGINAS);
		response.setTotal(RESPONSE_TOTAL);

		return response;
	}

}
