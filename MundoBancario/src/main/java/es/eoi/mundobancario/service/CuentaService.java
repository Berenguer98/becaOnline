package es.eoi.mundobancario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eoi.mundobancario.repository.CuentaRepository;
import es.eoi.mundobancario.MyExcepcion;
import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.dto.TipoMovimientoDto;
import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;
import es.eoi.mundobancario.entity.TipoMovimiento;

@Service
public class CuentaService {

	@Autowired
	CuentaRepository repository;
	
	@Autowired
	MovimientoService servicemovs;
	
	@Autowired
	PrestamoService servicepres;
	
	@Autowired
	AmortizacionService serviceamor;
	
	@Autowired
	ClienteService servicecli;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	public List<CuentaDto> findAll() {
		List<CuentaDto> cuentasdto=new ArrayList();
		
		for (Cuenta cuenta : repository.findAll()) {
			CuentaDto cuentaDto = new CuentaDto();
			int id_cliente=cuenta.getCliente().getId();
			cuenta.setMovimientos(null);
			cuenta.setPrestamos(null);
			BeanUtils.copyProperties(cuenta, cuentaDto);
			cuentaDto.setCliente(servicecli.findClienteDtoById(id_cliente));
			cuentasdto.add(cuentaDto);
			
		}
		return cuentasdto;
	}
	
	public List<CuentaDto> findAllSaldoNegative(){
		List<CuentaDto> cuentasdto=new ArrayList<CuentaDto>();
		
		for (Cuenta cuenta : repository.findAllSaldoNegative()) {
			CuentaDto cuentaDto = new CuentaDto();
			int id_cliente=cuenta.getCliente().getId();
			cuenta.setMovimientos(null);
			cuenta.setPrestamos(null);
			BeanUtils.copyProperties(cuenta, cuentaDto);
			cuentaDto.setCliente(servicecli.findClienteDtoById(id_cliente));
			cuentasdto.add(cuentaDto);
		}
		return cuentasdto;		
	}
	
	public CuentaDto findById(Integer id){
		Cuenta cuenta=repository.findById(id).get();
		int id_cliente=cuenta.getCliente().getId();
		cuenta.setMovimientos(null);
		cuenta.setPrestamos(null);
		CuentaDto cuentaDto = new CuentaDto();
		BeanUtils.copyProperties(cuenta, cuentaDto);
		cuentaDto.setCliente(servicecli.findClienteDtoById(id_cliente));
		return cuentaDto;
		
	}
	
	public Cuenta findByIdEntity(Integer id){
		Cuenta cuentaE=repository.findById(id).get();
		Cliente cliente=cuentaE.getCliente();
		cliente.setPass(null);
		cuentaE.setCliente(cliente);
		return cuentaE;
		
	}
	
	public void createCuenta(CuentaDto cuentadto) {
		
		repository.save(this.convertToEntity(cuentadto));
	}
	
	public void updateCuentaAliasById(String alias,int id) {
		Cuenta cuenta=repository.findById(id).get();
		cuenta.setAlias(alias);
		repository.save(cuenta);
	}
	
	
	public List<MovimientoDto> findMovimientosById(int id){
		return servicemovs.findAllMovs(id);
	}
	
	
	
	public void createPrestamo(PrestamoDto prestamo, int id) throws MyExcepcion {
		prestamo.getCuenta().setNum_cuenta(id);
		servicepres.crearPrestamo(prestamo);
		
		Cuenta cuenta= convertToEntity(findById(id));
		double importe=cuenta.getSaldo() + prestamo.getImporte();
		cuenta.setSaldo(importe);
		repository.save(cuenta);
		
		TipoMovimientoDto tipomovimiento=new TipoMovimientoDto();
		tipomovimiento.setId(2);
		tipomovimiento.setTipo("PRESTAMO");
		MovimientoDto movimiento=new MovimientoDto(prestamo.getDescripcion(),prestamo.getFecha(),prestamo.getImporte(),prestamo.getCuenta(),tipomovimiento);
		servicemovs.createMovimiento(movimiento);
		
	}
	
	
	public void createIngreso(MovimientoDto ingreso,int id) {
		//ingreso tiene id=1
		TipoMovimientoDto tipomovimiento=new TipoMovimientoDto();
		tipomovimiento.setId(1);
		tipomovimiento.setTipo("INGRESO");
		ingreso.setTipomovimiento(tipomovimiento);
		
		servicemovs.createMovimiento(ingreso);
		
		Cuenta cuenta= convertToEntity(findById(id));
		double importe=cuenta.getSaldo() + ingreso.getImporte();
		cuenta.setSaldo(importe);
		repository.save(cuenta);
	}
	
	public void createPago(MovimientoDto pago,int id) throws MyExcepcion{ 
		
		Cuenta cuenta= convertToEntity(findById(id));
		double saldo=cuenta.getSaldo();
		if(saldo<=0) {
			throw new MyExcepcion("El saldo de la cuenta es 0 o negativo por lo que no se puede realizar el pago");
		}
		double importe=cuenta.getSaldo() - pago.getImporte();
		cuenta.setSaldo(importe);
		repository.save(cuenta);
		
		//pago tiene id 3
		TipoMovimientoDto tipomovimiento=new TipoMovimientoDto();
		tipomovimiento.setId(2);
		tipomovimiento.setTipo("PAGO");
		pago.setTipomovimiento(tipomovimiento);
		
		servicemovs.createMovimiento(pago);
	}
	
	
	
	public List<PrestamoDto> findPrestamosByIdCuenta(int num_cuenta){
		Cuenta cuenta=findByIdEntity(num_cuenta);
		return servicepres.findPrestamos(cuenta);
	}
	
	public List<PrestamoDto> findPrestamosVivosByIdCuenta(int num_cuenta) throws MyExcepcion{
		Cuenta cuenta=findByIdEntity(num_cuenta);
		return servicepres.findPrestamosVivosByIdCuenta(cuenta);
	}
	
	public List<PrestamoDto> findPrestamosAmortizadosByIdCuenta(int num_cuenta) throws MyExcepcion{
		Cuenta cuenta=findByIdEntity(num_cuenta);
		return servicepres.findPrestamosAmortizadosByIdCuenta(cuenta);
	}
	
	
	public void amortizacionesDiarias() {
				TipoMovimiento tipoAmortizacion=new TipoMovimiento(4,"AMORTIZACION");
				TipoMovimiento tipoInteres=new TipoMovimiento(5,"INTERES");
		for (Amortizacion amortizacion : serviceamor.amortizacionesDiarias()){
			Movimiento movimientoAmortizacion=new Movimiento("Pago de amortizacion",amortizacion.getFecha(),amortizacion.getImporte(),amortizacion.getPrestamo().getCuenta(),tipoAmortizacion);
			servicemovs.createMovimientoE(movimientoAmortizacion);
			double interes=amortizacion.getImporte()*0.02;
			Movimiento movimientoInteres=new Movimiento("Interes amortizacion",amortizacion.getFecha(),interes,amortizacion.getPrestamo().getCuenta(),tipoInteres);
			servicemovs.createMovimientoE(movimientoInteres);
			//actualizamos la cuenta
			Cuenta cuenta= convertToEntity(findById(amortizacion.getPrestamo().getCuenta().getNum_cuenta()));
			double importe=cuenta.getSaldo() - amortizacion.getImporte() - interes;
			cuenta.setSaldo(importe);
			repository.save(cuenta);
		}
		
	}
	
	
	
	
	
	
	
	
	private CuentaDto convertToDto(Cuenta entity) {
		return modelMapper.map(entity, CuentaDto.class);
	}
	
	private Cuenta convertToEntity(CuentaDto dto) {	
	    return modelMapper.map(dto,Cuenta.class);	
	}
	
	
	
}
