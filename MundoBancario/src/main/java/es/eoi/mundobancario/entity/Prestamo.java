package es.eoi.mundobancario.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="Prestamos")
public class Prestamo {

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
	
	@Column(name="PLAZOS")
	private int  plazos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA", referencedColumnName = "NUM_CUENTA")
	private Cuenta cuenta;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prestamo")
	private List<Amortizacion> amortizaciones;

	
	public Prestamo(int id, String descripcion, Date fecha, double importe, int plazos) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.importe = importe;
		this.plazos = plazos;
	}
	
	
	public Prestamo() {
	}


	


	public Prestamo(int id, String descripcion, Date fecha, double importe, int plazos, Cuenta cuenta) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.importe = importe;
		this.plazos = plazos;
		this.cuenta = cuenta;
	}

	
	
	
	
	
	
}
