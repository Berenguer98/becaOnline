package es.eoi.mundobancario.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.MyExcepcion;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.repository.PrestamoRepository;
import es.eoi.mundobancario.repository.AmortizacionRepository;

@Service
public class PrestamoService {

	@Autowired
	PrestamoRepository repository;
	
	@Autowired
	AmortizacionService serviceamor;
	
	@Autowired
	CuentaService servicecuenta;
	
	@Autowired
	ModelMapper modelMapper;
	
	public void crearPrestamo(PrestamoDto prestamodto) throws MyExcepcion {
		Prestamo prestamo=convertToEntity(prestamodto);
		serviceamor.findAmortizacionNoPagada(prestamo.getCuenta().getNum_cuenta(), prestamo.getFecha());
		repository.save(prestamo);
		
		Calendar calendar=Calendar.getInstance();
		
		double importe_amortizacion=prestamo.getImporte()/prestamo.getPlazos();
		
		for (int i = 0; i < prestamo.getPlazos(); i++) {
			calendar.add(Calendar.MONTH, i+1);
			Date fecha=new Date(calendar.getTimeInMillis());
			Amortizacion amortizacion=new Amortizacion(fecha,importe_amortizacion,prestamo);
			serviceamor.createAmortizacion(amortizacion);		
		}
	}
	
	
	public List<PrestamoDto> findPrestamos(Cuenta cuenta){			
		
		List<PrestamoDto> prestamosdto=new ArrayList<PrestamoDto>();
		for (Prestamo prestamo : repository.findPrestamosByCuenta(cuenta)) {
			prestamo.setAmortizaciones(serviceamor.findAmortizacionesByPrestamo(prestamo));
			prestamosdto.add(convertToDto(prestamo));
		}
		
		return prestamosdto;
	}
	
	
	
	public List<PrestamoDto> findPrestamosVivosByIdCuenta(Cuenta cuenta) throws MyExcepcion{			
		
		List<PrestamoDto> prestamosdto=new ArrayList<PrestamoDto>();
		for (Prestamo prestamo : repository.findPrestamosByCuenta(cuenta)) {
			java.util.Date fechamax=serviceamor.findFechaAmortizacion(cuenta.getNum_cuenta(), prestamo);
			java.util.Date fecha_hoy=Calendar.getInstance().getTime();
			int idMax=serviceamor.idPrestamofechaAmortizacionMax(cuenta.getNum_cuenta(),fechamax);
			if(fechamax.compareTo(fecha_hoy)<0) {
				throw new MyExcepcion("No hay prestamos vivos");
			}else{
				if(idMax==prestamo.getId()) {
			prestamo.setAmortizaciones(serviceamor.findAmortizacionesByPrestamoVivo(cuenta.getNum_cuenta(),prestamo));
			prestamosdto.add(convertToDto(prestamo));
			}}}
		
		return prestamosdto;
	}
	
public List<PrestamoDto> findPrestamosAmortizadosByIdCuenta(Cuenta cuenta) throws MyExcepcion{			
		
		List<PrestamoDto> prestamosdto=new ArrayList<PrestamoDto>();
		for (Prestamo prestamo : repository.findPrestamosByCuenta(cuenta)) {
			
			
			java.util.Date fecha_amortizacion=serviceamor.findDateAmortizacion(cuenta.getNum_cuenta(), prestamo);
			java.util.Date fecha_sistema=Calendar.getInstance().getTime();
			
			if(fecha_amortizacion.compareTo(fecha_sistema)<0) {
				prestamo.setAmortizaciones(serviceamor.findAmortizacionesByPrestamoAmortizado(cuenta.getNum_cuenta(),prestamo));
				prestamosdto.add(convertToDto(prestamo));	
			}
			
		}
		
		if(prestamosdto.size()==0) {
			throw new MyExcepcion("No hay prestamos amortizados");
		}
		
		return prestamosdto;
	}

public PrestamoDto findPrestamoDtoById(int id) {
	return convertToDto(repository.findPrestamosById(id));
}

public List<PrestamoDto> findPrestamosVivos(){
	List<PrestamoDto> prestamosdto=new ArrayList<PrestamoDto>();
	for (Prestamo prestamoE : repository.findPrestamos()) {
		Prestamo prestamo=serviceamor.findVivosAmortizados(prestamoE);
		if(prestamo.getAmortizaciones()==null) {
			
		}else {
			prestamo.setAmortizaciones(null);
		prestamosdto.add(convertToDto(prestamo));
		}
	}
	
	return prestamosdto;
}


public List<PrestamoDto> findPrestamosAmortizados(){
	List<PrestamoDto> prestamosdto=new ArrayList<PrestamoDto>();
	for (Prestamo prestamoE : repository.findPrestamos()) {
		Prestamo prestamo=serviceamor.findVivosAmortizados(prestamoE);
		if(prestamo.getAmortizaciones()==null) {
			prestamosdto.add(convertToDto(prestamo));
		}else {

		}
	}
	
	return prestamosdto;
}

	
public Prestamo findPrestamoById(int id) {
	return repository.findPrestamosById(id);
}
	
public Integer findNum_cuentaByIdPrestamo(int id) {
	Integer num_cuenta=repository.findIdCuentaByIdPrestamo(id);
	return num_cuenta;
}
	
	
	
	private PrestamoDto convertToDto(Prestamo entity) {	
	    return modelMapper.map(entity,PrestamoDto.class);	
	}
	
	private Prestamo convertToEntity(PrestamoDto dto) {	
	    return modelMapper.map(dto,Prestamo.class);	
	}
}
