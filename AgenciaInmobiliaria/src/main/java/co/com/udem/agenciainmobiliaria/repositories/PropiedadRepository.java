package co.com.udem.agenciainmobiliaria.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliaria.entities.Propiedad;

public interface PropiedadRepository extends CrudRepository<Propiedad, Long> {
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor = ?1 OR u.numeroHabitaciones = ?1 OR u.areaMetrosCuadros = ?1 ")
	Iterable<Propiedad> findByUnParametro(double parametro);
	
	@Query("SELECT u FROM Propiedad u WHERE  (u.valor = ?1 AND u.numeroHabitaciones = CAST(?2 as integer)) "
			+ "OR (u.valor = ?1 AND u.areaMetrosCuadros = ?2)"
			+ "OR (u.numeroHabitaciones= CAST(?1 as integer) AND u.areaMetrosCuadros = ?2)")
	Iterable<Propiedad> findByDosParametros(double parametro1,double parametro2);
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor = ?1 AND u.numeroHabitaciones = ?2  AND u.areaMetrosCuadros = ?3")
	Iterable<Propiedad> findByTodos(double valor,int numeroHabitaciones, double areaMetrosCuadros);
	
	@Query("SELECT u FROM Propiedad u WHERE u.idUsuario = ?1")
	Iterable<Propiedad> findByUsuarioPropiedad(long idUsuario);

}
