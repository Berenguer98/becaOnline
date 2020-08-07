package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.repository.MovimientoRepository;

@Service
public class MovimientoService {

	@Autowired
	MovimientoRepository repository;
	
	@Autowired
	CuentaService servicecuenta;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<MovimientoDto> findAllMovs(Integer id){
		List<MovimientoDto> dtos = new ArrayList<MovimientoDto>();
		Cuenta cuenta=servicecuenta.findByIdEntity(id);
		for (Movimiento movimiento : repository.findAllMovs(cuenta)){
			dtos.add(convertToDto(movimiento));
		}
		return dtos;
	}
	
	public void createMovimiento(MovimientoDto movimiento) {
		repository.save(convertToEntity(movimiento));		
	}
	
	public void createMovimientoE(Movimiento movimiento) {
		repository.save(movimiento);		
	}	
	
	
	private MovimientoDto convertToDto(Movimiento entity) {	
	    return modelMapper.map(entity,MovimientoDto.class);	
	}
	
	private Movimiento convertToEntity(MovimientoDto dto) {	
	    return modelMapper.map(dto,Movimiento.class);	
	}
}
