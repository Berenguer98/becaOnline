package es.eoi.mundobancario.dto;

import java.util.List;

import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuentaDto {

	private Integer num_cuenta;
	
	private String alias;
	
	private double saldo;
	
	private ClienteDto cliente;
	
	private List<MovimientoDto> movimientos;
	
	private List<PrestamoDto> prestamos;

	/*public CuentaDto(Integer num_cuenta, String alias, double saldo, ClienteDto cliente) {
		super();
		this.num_cuenta = num_cuenta;
		this.alias = alias;
		this.saldo = saldo;
		this.cliente = cliente;
	}

	public CuentaDto(String alias, double saldo,ClienteDto cliente) {
		super();
		this.alias = alias;
		this.saldo = saldo;
		this.cliente = cliente;
	}
	
	public CuentaDto() {
	}
	*/
	
	
}
