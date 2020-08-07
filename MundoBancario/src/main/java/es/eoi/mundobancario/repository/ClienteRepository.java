package es.eoi.mundobancario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.eoi.mundobancario.entity.Cliente;
import es.eoi.mundobancario.entity.Cuenta;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, PagingAndSortingRepository<Cliente, Integer> {
	
@Query("select new es.eoi.mundobancario.entity.Cliente(c.id,c.usuario,c.nombre,c.email) from Cliente c")
List<Cliente> findAll();

@Query("select new es.eoi.mundobancario.entity.Cliente(c.id,c.usuario,c.nombre,c.email) from Cliente c where c.id = :id")
Cliente findClienteById(@Param("id") int id);

@Query("select new es.eoi.mundobancario.entity.Cliente(c.id,c.usuario,c.nombre,c.email) from Cliente c where c.usuario = :usuario and c.pass = :pass")
Cliente findByUsuarioAndPass(@Param("usuario") String usuario,@Param("pass") String pass);

@Query("select c.id,c.usuario,c.pass,c.nombre,c.email from Cliente c where c.usuario = :usuario")
Cliente findByUsuario(@Param("usuario") String usuario);

@Query("select new es.eoi.mundobancario.entity.Cuenta(c.num_cuenta,c.alias,c.saldo) from Cuenta c where c.cliente= :cliente")
List<Cuenta> findCuentasByIdCliente(@Param("cliente") Cliente cliente);

@Query("select new es.eoi.mundobancario.entity.Cliente(c.id,c.usuario,c.pass,c.nombre,c.email) from Cliente c where c.id = :id")
Cliente findById(@Param("id") int id);

}
