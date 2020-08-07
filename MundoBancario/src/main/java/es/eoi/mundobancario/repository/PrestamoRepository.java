package es.eoi.mundobancario.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Cuenta;
import es.eoi.mundobancario.entity.Movimiento;
import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer>, PagingAndSortingRepository<Prestamo, Integer>{

@Query("select new es.eoi.mundobancario.entity.Prestamo(p.id,p.descripcion,p.fecha,p.importe,p.plazos) from Prestamo p where p.cuenta= :cuenta")
List<Prestamo> findPrestamosByCuenta(@Param("cuenta") Cuenta cuenta);

@Query("select new es.eoi.mundobancario.entity.Prestamo(p.id,p.descripcion,p.fecha,p.importe,p.plazos) from Prestamo p where p.id= :id")
Prestamo findPrestamosById(@Param("id") int id);

@Query(value="select id_cuenta from prestamos where id= :id",nativeQuery=true)
int findIdCuentaByIdPrestamo(@Param("id") int id);

@Query("select new es.eoi.mundobancario.entity.Prestamo(p.id,p.descripcion,p.fecha,p.importe,p.plazos) from Prestamo p")
List<Prestamo> findPrestamos();
}
