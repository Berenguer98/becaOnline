package es.eoi.mundobancario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer>, PagingAndSortingRepository<Movimiento, Integer>{
@Query("select new es.eoi.mundobancario.entity.Movimiento(c.id,c.descripcion,c.fecha,c.importe,c.tipomovimiento) from Movimiento c where c.cuenta= :cuenta")
List<Movimiento> findAllMovs(@Param("cuenta") Cuenta cuenta);
}
