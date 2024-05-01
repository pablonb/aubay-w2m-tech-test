package com.aubay.w2m.dto;

import java.util.ArrayList;
import java.util.List;

import com.aubay.w2m.model.Nave;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaveListResponseDto {
	
	private List<Nave> naves = new ArrayList<>();
	private int pagina;
	private int total;
	private int paginas;

}
