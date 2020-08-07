package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.repository.ClienteRepository;
import es.eoi.mundobancario.repository.CuentaRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	MovimientoService servicemovimiento;
	
	@Autowired
	PrestamoService serviceprestamo;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<ClienteDto> findAll() {			
		return repository.findAll().
				stream().
				map(this::convertToDto).
				collect(Collectors.toList());
	}
	
	public ClienteDto findClienteDtoById(int id) {
		return convertToDto(repository.findClienteById(id));
	}
	
	public Cliente findClienteById(int id) {
		return repository.findClienteById(id);
	}
	
	public ClienteDto findByUsuarioAndPass(String usuario, String pass) {
		return convertToDto(repository.findByUsuarioAndPass(usuario, pass));
	}
	
	
	public void createCliente(ClienteDto cliente) {
		repository.save(convertToEntity(cliente));
	}
	
	public List<CuentaDto> findCuentasByIdCliente(int id){
		List<CuentaDto> dtos = new ArrayList<CuentaDto>();

		for (Cuenta cuenta : repository.findCuentasByIdCliente(repository.findById(id))){
			
			CuentaDto cuentaDto = new CuentaDto();
			BeanUtils.copyProperties(cuenta, cuentaDto);
			cuentaDto.setMovimientos(servicemovimiento.findAllMovs(cuenta.getNum_cuenta()));
			cuentaDto.setPrestamos(serviceprestamo.findPrestamos(cuenta));
			dtos.add(cuentaDto);
		}

		return dtos;
	}
	
	
	public void updateClienteEmailById(String email,int id) {
		Cliente cliente=repository.findById(id);
		cliente.setEmail(email);
		repository.save(cliente);
	}
	
	
	
	
	
	private ClienteDto convertToDto(Cliente	entity) {	
	    return modelMapper.map(entity,ClienteDto.class);	
	}
	
	private Cliente convertToEntity(ClienteDto dto) {	
	    return modelMapper.map(dto,Cliente.class);	
	}
	
}
