package es.eoi.mundobancario.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.service.AmortizacionService;
import es.eoi.mundobancario.service.ClienteService;
import es.eoi.mundobancario.service.CuentaService;
import es.eoi.mundobancario.service.MovimientoService;
import es.eoi.mundobancario.service.PrestamoService;
import es.eoi.mundobancario.service.ReportsService;

@RestController
public class ReportsController {

	@Autowired
	ReportsService service;
	
	@Autowired
	ClienteService servicecliente;
	
	@Autowired
	CuentaService servicecuenta;
	
	@Autowired
	MovimientoService servicemovimiento;
	
	@Autowired
	PrestamoService serviceprestamo;
	
	@Autowired
	AmortizacionService serviceamortizacion;
	
	@GetMapping("reports/clientes/{id}")
	public ResponseEntity<ClienteDto> findClientesCuentasMovs(@PathVariable int id){
		return ResponseEntity.ok(service.findClientesCuentasMovs(id));
	}
	
	@PostMapping("reports/clientes/{id}")
	public ResponseEntity<String> imprimirPDF_EOI_BANK_CLIENTE_000(@PathVariable int id) throws FileNotFoundException, DocumentException{
		service.imprimirPDF_EOI_BANK_CLIENTE_000(id);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping("reports/prestamos/{id}")
	public ResponseEntity<ClienteDto> findClientePrestamoByPrestamoId(@PathVariable int id){
		return ResponseEntity.ok(service.findClientePrestamoByPrestamoId(id));
	}
	
	@PostMapping("reports/prestamos/{id}")
	public ResponseEntity<String> imprimirPDF_EOI_BANK_PRESTAMO_000(@PathVariable int id) throws FileNotFoundException, DocumentException{
		service.imprimirPDF_EOI_BANK_PRESTAMO_000(id);;
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	
	
	@GetMapping("reports/prestamosVivos")
	public ResponseEntity<List<PrestamoDto>> findPrestamosVivos(){
		return ResponseEntity.ok(service.findPrestamosVivosCuentaCliente());
	}
	
	@GetMapping("reports/prestamosAmortizados")
	public ResponseEntity<List<PrestamoDto>> findPrestamosAmortizados(){
		return ResponseEntity.ok(service.findPrestamosAmortizadosCuentaCliente());
	}

}
