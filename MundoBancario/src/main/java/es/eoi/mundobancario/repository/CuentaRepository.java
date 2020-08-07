package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.TipoMovimiento;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>, PagingAndSortingRepository<Cuenta, Integer> {

@Query("select c from Cuenta c where c.saldo < 0")
public List<Cuenta> findAllSaldoNegative();
	
@Query("select c from Cuenta c where c.cliente= :cliente")
List<Cuenta> findCuentasByCliente(@Param("cliente") Cliente cliente);

}
