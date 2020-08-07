package es.eoi.mundobancario.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Amortizacion;
import es.eoi.mundobancario.entity.Prestamo;

@Repository
public interface AmortizacionRepository extends JpaRepository<Amortizacion, Integer>, PagingAndSortingRepository<Amortizacion, Integer>{

@Query(value="select max(a.fecha) from AMORTIZACIONES a where a.id_prestamo= :id", nativeQuery=true)
Date fechaAmortizacion(@Param("id") int id_prestamo);

@Query(value="select max(a.fecha) from AMORTIZACIONES a,PRESTAMOS P where a.id_prestamo= p.id AND p.id_cuenta= :id_cuenta", nativeQuery=true)
Date fechaAmortizacionMax(@Param("id_cuenta") int id_cuenta);

@Query(value="select a.id_prestamo from AMORTIZACIONES a,PRESTAMOS p where a.id_prestamo=p.id AND p.id_cuenta= :id_cuenta AND a.fecha= :fecha", nativeQuery=true)
int idPrestamofechaAmortizacionMax(@Param("id_cuenta") int id_cuenta,@Param("fecha") String fecha);
	
@Query("select new es.eoi.mundobancario.entity.Amortizacion(a.id,a.fecha,a.importe) from Amortizacion a where a.prestamo= :prestamo")
List<Amortizacion> findAmortizacionesByPrestamo(@Param("prestamo") Prestamo prestamo);

@Query(value="select id,fecha,importe,id_prestamo from amortizaciones",nativeQuery=true)
List<Amortizacion> findAmortizacionesByIdPrestamo();

//@Query("select new es.eoi.mundobancario.entity.Amortizacion(a.fecha,a.importe,a.prestamo) from Amortizacion a where a.fecha= :fecha")
@Query(value="select id,fecha,importe,id_prestamo from amortizaciones a where fecha= :fecha",nativeQuery=true)
List<Amortizacion> findAmortizacionesByFecha(@Param("fecha") Date fecha);

}
