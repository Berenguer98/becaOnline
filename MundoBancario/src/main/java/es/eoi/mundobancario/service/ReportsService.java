package es.eoi.mundobancario.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

import es.eoi.mundobancario.ITextPDF;
import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Movimiento;

@Service
public class ReportsService {
	
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

	public ClienteDto findClientesCuentasMovs(int id) {
				ClienteDto clientedto=servicecliente.findClienteDtoById(id);
				List<CuentaDto> dtos = new ArrayList<CuentaDto>();
				for (CuentaDto cuentadto : servicecliente.findCuentasByIdCliente(id)){
					int num_cuenta=cuentadto.getNum_cuenta();
					cuentadto.setMovimientos(servicecuenta.findMovimientosById(num_cuenta));
					dtos.add(cuentadto);
				}
				clientedto.setCuentas(dtos);
				return clientedto;
	}
	
	public void imprimirPDF_EOI_BANK_CLIENTE_000(int id) throws FileNotFoundException, DocumentException {
		ITextPDF.imprimirPDF_EOI_BANK_CLIENTE_000(findClientesCuentasMovs(id));
	}
	
	public ClienteDto findClientePrestamoByPrestamoId(int id_prestamo) {
		
		List<PrestamoDto> prestamos=new ArrayList<PrestamoDto>();
		PrestamoDto prestamodto=serviceprestamo.findPrestamoDtoById(id_prestamo);
		List<AmortizacionDto> amortizaciones= new ArrayList<AmortizacionDto>(serviceamortizacion.findAmortizacionesDtoByPrestamo(serviceprestamo.findPrestamoById(id_prestamo)));
		prestamodto.setAmortizaciones(amortizaciones);
		prestamos.add(prestamodto);
		
		CuentaDto cuenta=servicecuenta.findById(serviceprestamo.findNum_cuentaByIdPrestamo(id_prestamo));
		ClienteDto clientedto=servicecliente.findClienteDtoById(cuenta.getCliente().getId());
		
		CuentaDto cuentadto=new CuentaDto();
		cuentadto.setPrestamos(prestamos);
		cuentadto.setNum_cuenta(cuenta.getNum_cuenta());
		List<CuentaDto> cuentas=new ArrayList<CuentaDto>();
		cuentas.add(cuentadto);
		clientedto.setCuentas(cuentas);
		
		return clientedto;
	}
	
	public void imprimirPDF_EOI_BANK_PRESTAMO_000(int id) throws FileNotFoundException, DocumentException{
		ITextPDF.imprimirPDF_EOI_BANK_PRESTAMO_000(findClientePrestamoByPrestamoId(id));
	}
	
	public List<PrestamoDto> findPrestamosVivosCuentaCliente(){
		
		List<PrestamoDto> prestamos=new ArrayList<PrestamoDto>();
		
		for (PrestamoDto prestamodto : serviceprestamo.findPrestamosVivos()){
			int num_cuenta=serviceprestamo.findNum_cuentaByIdPrestamo(prestamodto.getId());
			prestamodto.setCuenta(servicecuenta.findById(num_cuenta));
			prestamodto.getCuenta().setMovimientos(null);
			prestamodto.getCuenta().setPrestamos(null);
			prestamos.add(prestamodto);
		}
		return prestamos;
	}
	
	
public List<PrestamoDto> findPrestamosAmortizadosCuentaCliente(){
		
		List<PrestamoDto> prestamos=new ArrayList<PrestamoDto>();
		
		for (PrestamoDto prestamodto : serviceprestamo.findPrestamosAmortizados()){
			int num_cuenta=serviceprestamo.findNum_cuentaByIdPrestamo(prestamodto.getId());
			prestamodto.setCuenta(servicecuenta.findById(num_cuenta));
			prestamodto.getCuenta().setMovimientos(null);
			prestamodto.getCuenta().setPrestamos(null);
			prestamos.add(prestamodto);
		}
		return prestamos;
	}
	
	

}
