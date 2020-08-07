package es.eoi.mundobancario.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrestamoDto {
	private int id;

	private String descripcion;
	
	private Date fecha;
	
	private double importe;
	
	private Integer plazos;
	
	private CuentaDto cuenta;
	
	private List<AmortizacionDto> amortizaciones;
	
	
	
}
