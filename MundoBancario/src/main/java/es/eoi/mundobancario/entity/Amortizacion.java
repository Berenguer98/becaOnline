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
@Table(name="AMORTIZACIONES")
public class Amortizacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="FECHA")
	private Date fecha;
	
	@Column(name="IMPORTE")
	private double importe;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRESTAMO", referencedColumnName = "ID")
	private Prestamo prestamo;

	public Amortizacion(Date fecha, double importe, Prestamo prestamo) {
		super();
		this.fecha = fecha;
		this.importe = importe;
		this.prestamo = prestamo;
	}

	public Amortizacion() {
		super();
	}

	public Amortizacion(Integer id, Date fecha, double importe) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.importe = importe;
	}

	public Amortizacion(Integer id, Date fecha, double importe, Prestamo prestamo) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.importe = importe;
		this.prestamo = prestamo;
	}
	
	
	
	
}
