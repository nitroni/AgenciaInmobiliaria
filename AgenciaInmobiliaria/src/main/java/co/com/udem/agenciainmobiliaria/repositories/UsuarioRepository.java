package co.com.udem.agenciainmobiliaria.repositories;

import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliaria.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
