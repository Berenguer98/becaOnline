package es.eoi.mundobancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.eoi.mundobancario.service.CuentaService;
import es.eoi.mundobancario.MyExcepcion;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.repository.AmortizacionRepository;

@RestController
public class CuentaController {

	@Autowired
	CuentaService service;
	
	@Autowired
	AmortizacionRepository repo;
	
	@GetMapping("cuentas")
	public ResponseEntity<List<CuentaDto>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("cuentas/deudoras")
	public ResponseEntity<List<CuentaDto>> findAllSaldoNegative(){
		return ResponseEntity.ok(service.findAllSaldoNegative());
	}
	
	@GetMapping("cuentas/{id}")
	public ResponseEntity<CuentaDto> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping("cuentas")
	public ResponseEntity<String> createCuenta(@RequestBody CuentaDto cuenta){
		service.createCuenta(cuenta);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("cuentas/{id}")
	public ResponseEntity<String> updateCuentaAliasById(@PathVariable int id, @RequestParam String alias){
		service.updateCuentaAliasById(alias, id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping("cuentas/{id}/movimientos")
	public ResponseEntity<List<MovimientoDto>> findMovimientosById(@PathVariable int id){
		return ResponseEntity.ok(service.findMovimientosById(id));		
	}
	
	
	@PostMapping("cuentas/{id}/prestamos")
	public ResponseEntity<String> createPrestamo(@PathVariable int id, @RequestBody PrestamoDto prestamo) throws MyExcepcion{
		service.createPrestamo(prestamo, id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("cuentas/{id}/ingresos")
	public ResponseEntity<String> createIngreso(@PathVariable int id, @RequestBody MovimientoDto ingreso){
		service.createIngreso(ingreso,id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping("cuentas/{id}/pagos")
	public ResponseEntity<String> createPago(@PathVariable int id, @RequestBody MovimientoDto pago) throws MyExcepcion{
		service.createPago(pago,id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping("cuentas/{id}/prestamos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosByIdCuenta(@PathVariable int id){
		return ResponseEntity.ok(service.findPrestamosByIdCuenta(id));
	}
	
	@GetMapping("cuentas/{id}/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosVivosByIdCuenta(@PathVariable int id) throws MyExcepcion{
		return ResponseEntity.ok(service.findPrestamosVivosByIdCuenta(id));
	}
	
	@GetMapping("cuentas/{id}/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> findPrestamosAmortizadosByIdCuenta(@PathVariable int id) throws MyExcepcion{
		return ResponseEntity.ok(service.findPrestamosAmortizadosByIdCuenta(id));
	}
	
	@PostMapping("cuentas/ejecutarAmortizacionesDiarias")
	public ResponseEntity<String> amortizacionesDiarias(){
		service.amortizacionesDiarias();
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
}
