package co.com.udem.agenciainmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.agenciainmobiliaria.dto.PropiedadDTO;
import co.com.udem.agenciainmobiliaria.entities.Propiedad;
import co.com.udem.agenciainmobiliaria.entities.Usuario;
import co.com.udem.agenciainmobiliaria.repositories.PropiedadRepository;
import co.com.udem.agenciainmobiliaria.repositories.UsuarioRepository;
import co.com.udem.agenciainmobiliaria.security.jwt.JwtTokenProvider;
import co.com.udem.agenciainmobiliaria.util.Constantes;
import co.com.udem.agenciainmobiliaria.util.ConvertPropiedad;
import java.util.Date;

@RestController
public class PropiedadRestController {
	
	@Autowired
	private PropiedadRepository propiedadRepository;

	@Autowired
	private ConvertPropiedad convertPropiedad;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/agenciainmobiliaria/adicionarPropiedad")
    public Map<String, String> adicionarPropiedad(@RequestHeader("Authorization") String token, @RequestBody PropiedadDTO propiedadDTO) {
       
		String[] parts = token.split(" ");
		token = parts[1];
		String username = jwtTokenProvider.getUsername(token);
		Date Fecha = new Date();   
		
		Map<String, String> response = new HashMap<>();
        try {
        	
        	Usuario usuario = usuarioRepository.findByNumeroIdentificacionPropiedad(username);
                	
        	Propiedad propiedad = convertPropiedad.convertToEntity(propiedadDTO);
        	propiedad.setCodigo(Fecha.getTime());
        	propiedad.setIdUsuario(usuario.getId());
        	
        	propiedadRepository.save(propiedad);           
            response.put(Constantes.CODIGO_HTTP, "200");
            response.put(Constantes.MENSAJE_EXITO, "La propiedad fuera registrada exitosamente");
            
            return response;
        } catch (ParseException e) {
            response.put(Constantes.CODIGO_HTTP, "500");
            response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al registrar la propiedad");
            return response;
        }
       
    }
	
	
	//Filtro por un campo
	@GetMapping("/agenciainmobiliaria/filtropropiedad/{parametro}")
	public Iterable<PropiedadDTO> filtoPropiedad(@PathVariable double parametro) {		

		Iterable<Propiedad> prodiedad = propiedadRepository.findByUnParametro(parametro);
		
		Iterable<PropiedadDTO> propiedadDTO = null;
		try {
			propiedadDTO = convertPropiedad.listConvertToDTO(prodiedad);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return propiedadDTO;
	}
	
	//Filtro por dos campos	
	@GetMapping("/agenciainmobiliaria/filtropropiedad/{parametro1}/{parametro2}")
	public Iterable<PropiedadDTO> filtoPropiedad(@PathVariable double parametro1,@PathVariable double parametro2) {		

		Iterable<Propiedad> prodiedad = propiedadRepository.findByDosParametros(parametro1,parametro2);
		Iterable<PropiedadDTO> propiedadDTO = null;
		try {
			propiedadDTO = convertPropiedad.listConvertToDTO(prodiedad);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return propiedadDTO;
	}
	
	//Filtro por los 3 campos
	@GetMapping("/agenciainmobiliaria/filtropropiedad/{valor}/{numeroHabitaciones}/{areaMetrosCuadros}")
	public Iterable<PropiedadDTO> filtoPropiedad(@PathVariable double valor,@PathVariable int numeroHabitaciones, @PathVariable double areaMetrosCuadros) {		
		
		Iterable<Propiedad> prodiedad = propiedadRepository.findByTodos(valor,numeroHabitaciones,areaMetrosCuadros);
		
		Iterable<PropiedadDTO> propiedadDTO = null;
		try {
			propiedadDTO = convertPropiedad.listConvertToDTO(prodiedad);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return propiedadDTO;
	}

	
	//Buscar una propiedad en espesifico
	@GetMapping("/agenciainmobiliaria/propiedad/{id}")
	public PropiedadDTO buscarPropiedad(@PathVariable Long id) {
		
		PropiedadDTO propiedadDTO = null;
		try {
			propiedadDTO = convertPropiedad.ConvertToDTO(propiedadRepository.findById(id).get());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return propiedadDTO;		
	}
	
	
	//Listar todas las propiedades del usuario que se autentico
	@GetMapping("/agenciainmobiliaria/propiedadesUsuario")
	public Iterable<PropiedadDTO> listarPropiedadesPorUsuario(@RequestHeader("Authorization") String token){
		
		String[] parts = token.split(" ");
		token = parts[1];
		String username = jwtTokenProvider.getUsername(token);
		
		Iterable<PropiedadDTO> propiedadDTO = null;
		try {
			Usuario usuario = usuarioRepository.findByNumeroIdentificacionPropiedad(username);
			propiedadDTO = convertPropiedad.listConvertToDTO(propiedadRepository.findByUsuarioPropiedad(usuario.getId()));			
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		return propiedadDTO;			
	}
	
	//Listar todas las propiedades
	@GetMapping("/agenciainmobiliaria/propiedades")
	public Iterable<PropiedadDTO> listarPropiedades(){
		
		Iterable<PropiedadDTO> propiedadDTO = null;
		try {
			propiedadDTO = convertPropiedad.listConvertToDTO(propiedadRepository.findAll());
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		return propiedadDTO;			
	}
	
	//Modificar una propiedad
	@PutMapping("/agenciainmobiliaria/propiedad/{id}")
	public ResponseEntity<Object> updatePropiedad(@RequestBody PropiedadDTO newPropiedad, @PathVariable Long id){
		if(propiedadRepository.findById(id).isPresent()) {
			Propiedad propiedad = propiedadRepository.findById(id).get();
			
			propiedad.setNumeroBanos(newPropiedad.getNumeroBanos());
			propiedad.setNumeroHabitaciones(newPropiedad.getNumeroHabitaciones());
			propiedad.setValor(newPropiedad.getValor());
			propiedad.setAreaMetrosCuadros(newPropiedad.getAreaMetrosCuadros());
			propiedad.setTipoRegistro(newPropiedad.getTipoRegistro());
			
			propiedadRepository.save(propiedad);
			
			return ResponseEntity.ok("Se actualizó exitosamente");
			
		}
        else {
             return null;
        }
		
	}
	
	//Eliminar una propiedad
	@DeleteMapping("/agenciainmobiliaria/propiedad/{id}")
    public Map<String, String> eliminarPropiedad(@PathVariable Long id) {
		
		Map<String, String> response = new HashMap<>();
		try {
		    propiedadRepository.deleteById(id);
   	        response.put(Constantes.CODIGO_HTTP, "200");
   	        response.put(Constantes.MENSAJE_EXITO, "La propiedad se eliminado exitosamente");
		}
		catch (Exception e) {
			response.put(Constantes.CODIGO_HTTP, "500");
	        response.put(Constantes.MENSAJE_EXITO, "Se presento un error al eliminar la propiedad ó la propiedad con el Id "+id+" no existe.");
		}
		
		return response;
    }
	
}
