package es.eoi.mundobancario.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimientoDto {

	private String descripcion;
	
	private Date fecha;
	
	private double importe;
	
	private CuentaDto cuenta;
	
	private TipoMovimientoDto tipomovimiento;

	public MovimientoDto() {
		super();
	}

	public MovimientoDto(String descripcion, Date fecha, double importe, CuentaDto cuenta,
			TipoMovimientoDto tipomovimiento) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.importe = importe;
		this.cuenta = cuenta;
		this.tipomovimiento = tipomovimiento;
	}
	
	
	
}
