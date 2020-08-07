package es.eoi.mundobancario.service;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.MyExcepcion;
import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.repository.AmortizacionRepository;

@Service
public class AmortizacionService {

	@Autowired
	AmortizacionRepository repository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public void createAmortizacion(Amortizacion amortizacion) {
			repository.save(amortizacion);		
		
	}
	
	public void findAmortizacionNoPagada(int num_cuenta,Date fecha_prestamo) throws MyExcepcion {
		Date fecha_amortizacion=repository.fechaAmortizacion(num_cuenta);
		
		if(fecha_amortizacion.compareTo(fecha_prestamo)>0) {
			throw new MyExcepcion("Hay un prestamo sin liquidar");
		}
	}
	
	
	public List<Amortizacion> findAmortizacionesByPrestamo(Prestamo prestamo){
		return repository.findAmortizacionesByPrestamo(prestamo);
		
	}
	
	public List<AmortizacionDto> findAmortizacionesDtoByPrestamo(Prestamo prestamo){
		
		List<AmortizacionDto> dtos=new ArrayList<AmortizacionDto>();
		for (Amortizacion amortizacion : repository.findAmortizacionesByPrestamo(prestamo)) {
			dtos.add(convertToDto(amortizacion));
		}
		return dtos;
	}

	public List<Amortizacion> findAmortizacionesByPrestamoVivo(int num_cuenta,Prestamo prestamo) throws MyExcepcion{
		
		return repository.findAmortizacionesByPrestamo(prestamo);
	}
	
	public List<Amortizacion> findAmortizacionesByPrestamoAmortizado(int num_cuenta,Prestamo prestamo) throws MyExcepcion{
		
		
		return repository.findAmortizacionesByPrestamo(prestamo);
	}
	
	public Date findDateAmortizacion(int num_cuenta,Prestamo prestamo) {
		Date fecha_amortizacion=repository.fechaAmortizacion(prestamo.getId());
		return fecha_amortizacion;
		}
	
	public Date findFechaAmortizacion(int num_cuenta,Prestamo prestamo) {
		Date fecha_amortizacion=repository.fechaAmortizacionMax(num_cuenta);
		return fecha_amortizacion;
	}
	
	public int idPrestamofechaAmortizacionMax(int num_cuenta,Date fecha) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return repository.idPrestamofechaAmortizacionMax(num_cuenta,formatter.format(fecha));
	}
	
	public List<Amortizacion> amortizacionesDiarias(){
		java.sql.Date fecha_sistema=new java.sql.Date(Calendar.getInstance().getTime().getTime());
		return repository.findAmortizacionesByFecha(fecha_sistema);
	}

	public Prestamo  findVivosAmortizados(Prestamo prestamo) {
		Date fecha_amortizacion=repository.fechaAmortizacion(prestamo.getId());
		Date fecha_hoy=Calendar.getInstance().getTime();
		if(fecha_amortizacion.compareTo(fecha_hoy)<0) {
			prestamo.setAmortizaciones(null);
		}else {
			List<Amortizacion> amortizaciones=new ArrayList<Amortizacion>();
			for(Amortizacion amor:repository.findAmortizacionesByIdPrestamo()) {
				if(amor.getPrestamo().getId()==prestamo.getId()) {
					amortizaciones.add(amor);
				}
		}
			prestamo.setAmortizaciones(amortizaciones);
		}
		
		
		return prestamo;
	}
	
	
	
	
	private AmortizacionDto convertToDto(Amortizacion entity) {	
	    return modelMapper.map(entity,AmortizacionDto.class);	
	}
	
	private Amortizacion convertToEntity(AmortizacionDto dto) {	
	    return modelMapper.map(dto,Amortizacion.class);	
	}
	
	
}
