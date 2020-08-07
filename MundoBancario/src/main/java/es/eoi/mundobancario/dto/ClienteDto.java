package es.eoi.mundobancario.dto;

import java.util.List;

import es.eoi.mundobancario.entity.Cuenta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {
	private int id;
	
	private String usuario;
	
	private String pass;
	
	private String nombre;
	
	private String email;
	
	private List<CuentaDto> cuentas;
}
