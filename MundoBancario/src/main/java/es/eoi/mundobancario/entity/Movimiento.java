package es.eoi.mundobancario.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="MOVIMIENTOS")
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="FECHA")
	private Date fecha;
	
	@Column(name="IMPORTE")
	private double importe;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA", referencedColumnName = "NUM_CUENTA")
	private Cuenta cuenta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO", referencedColumnName = "ID")
	private TipoMovimiento tipomovimiento;

	public Movimiento(Integer id, String descripcion, Date fecha, double importe, TipoMovimiento tipomovimiento) {
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.importe = importe;
		this.tipomovimiento = tipomovimiento;
	}

	public Movimiento() {
	}

	public Movimiento(String descripcion, Date fecha, double importe, Cuenta cuenta, TipoMovimiento tipomovimiento) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.importe = importe;
		this.cuenta = cuenta;
		this.tipomovimiento = tipomovimiento;
	}

	
	
	
}
