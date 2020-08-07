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
@Table(name="CLIENTES")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="USUARIO")
	private String usuario;
	
	@Column(name="PASS")
	private String pass;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="EMAIL")
	private String email;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
	private List<Cuenta> cuentas;
	
	public Cliente(int id, String usuario, String nombre, String email) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.nombre = nombre;
		this.email = email;
	}

	
	
	public Cliente() {
	}



	public Cliente(String usuario, String pass, String nombre, String email) {
		this.usuario = usuario;
		this.pass = pass;
		this.nombre = nombre;
		this.email = email;
	}



	public Cliente(int id, String usuario, String pass, String nombre, String email) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pass = pass;
		this.nombre = nombre;
		this.email = email;
	}



	
	
	

	
}
