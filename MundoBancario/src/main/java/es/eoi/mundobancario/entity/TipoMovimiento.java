package es.eoi.mundobancario.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Tipos_Movimiento")
public class TipoMovimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="TIPO")
	private String tipo;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipomovimiento")
	private List<Movimiento> movimientos;

	public TipoMovimiento(Integer id, String tipo) {
		this.id = id;
		this.tipo = tipo;
	}

	public TipoMovimiento() {
	}
	
	
	
}
