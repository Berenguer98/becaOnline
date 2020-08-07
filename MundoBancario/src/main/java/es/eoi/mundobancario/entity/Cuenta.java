package es.eoi.mundobancario.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="CUENTAS")
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NUM_CUENTA")
	private int num_cuenta;
	
	@Column(name="ALIAS")
	private String alias;
	
	@Column(name="SALDO")
	private double saldo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
	private Cliente cliente;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
	private List<Movimiento> movimientos;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
	private List<Prestamo> prestamos;

	public Cuenta() {
		
	}

	public Cuenta(int num_cuenta, String alias, double saldo) {
		super();
		this.num_cuenta = num_cuenta;
		this.alias = alias;
		this.saldo = saldo;
	}

	

	public Cuenta(String alias, double saldo) {
		this.alias = alias;
		this.saldo = saldo;
	}

	

	
	
	
	
}
