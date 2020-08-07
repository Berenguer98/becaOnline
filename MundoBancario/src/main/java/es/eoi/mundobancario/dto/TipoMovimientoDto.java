package es.eoi.mundobancario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipoMovimientoDto {
	private int id;
	private String tipo;
	
	public TipoMovimientoDto(int id, String tipo) {
		this.id = id;
		this.tipo = tipo;
	}
	public TipoMovimientoDto() {
	}
	
	
}
