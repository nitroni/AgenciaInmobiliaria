package co.com.udem.agenciainmobiliaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliaria.entities.Usuario;



public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	Optional<Usuario> findByNumeroIdentificacion(String numeroIdentificacion);
	
	@Query("SELECT u FROM Usuario u WHERE u.numeroIdentificacion = ?1")
	Usuario findByNumeroIdentificacionPropiedad(String numeroIdentificacion);
	
}
