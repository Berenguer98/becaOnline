package es.eoi.mundobancario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.service.ClienteService;

@RestController
public class ClienteController {

	@Autowired
	ClienteService service;
	
	@GetMapping("clientes")
	public ResponseEntity<List<ClienteDto>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}	
	
	@GetMapping("clientes/{id}")	
	public ResponseEntity<ClienteDto> findClienteById(@PathVariable int id) {
		return ResponseEntity.ok(service.findClienteDtoById(id));
	}	
	
	@PostMapping("clientes/login")
	public ResponseEntity<ClienteDto> findClienteByUsuarioAndPass( @RequestParam String usuario, @RequestParam String pass) {
		return ResponseEntity.ok(service.findByUsuarioAndPass(usuario,pass));
	}
	
	@PostMapping("clientes")
	public ResponseEntity<String> createCliente(@RequestBody ClienteDto cliente){
		service.createCliente(cliente);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("clientes/{id}")
	public ResponseEntity<String> updateClienteEmailById(@PathVariable int id, @RequestParam String email){
		service.updateClienteEmailById(email, id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	@GetMapping("clientes/{id}/cuentas")
	public ResponseEntity<List<CuentaDto>> findCuentasById(@PathVariable int id){
		return ResponseEntity.ok(service.findCuentasByIdCliente(id));		
	}

	
}
