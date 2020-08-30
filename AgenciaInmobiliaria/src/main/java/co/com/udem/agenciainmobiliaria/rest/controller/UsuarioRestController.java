package co.com.udem.agenciainmobiliaria.rest.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.entities.TiposDeIdentificacion;
import co.com.udem.agenciainmobiliaria.entities.Usuario;
import co.com.udem.agenciainmobiliaria.repositories.UsuarioRepository;
import co.com.udem.agenciainmobiliaria.util.ConvertUsuario;
import co.com.udem.agenciainmobiliaria.util.Constantes;
import co.com.udem.agenciainmobiliaria.repositories.TiposDeIdentificacionRepository;

@RestController
public class UsuarioRestController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TiposDeIdentificacionRepository tiposDeIdentificacionRepository;
	
	@Autowired
	private ConvertUsuario convertUsuario;
	
	private Boolean valido = false;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/agenciainmobiliaria/adicionarUsuario")
    public Map<String, String> adicionarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Usuario usuario = convertUsuario.convertToEntity(usuarioDTO);
            
            //Validamos si el número identificación ya esta registrado
            Usuario usuarioValidar = usuarioRepository.findByNumeroIdentificacionPropiedad(usuario.getNumeroIdentificacion());
          
   	        Iterable<TiposDeIdentificacion> iTiposIdentificacion = tiposDeIdentificacionRepository.findAll();
            List<TiposDeIdentificacion> listaTiposIdentificacion = new ArrayList<TiposDeIdentificacion>();
            iTiposIdentificacion.iterator().forEachRemaining(listaTiposIdentificacion::add);
                
            for(int i = 0; i < listaTiposIdentificacion.size(); i++) {            	 
    	         if(usuario.getTipoIdentificacion().equals(listaTiposIdentificacion.get(i).getTipoIdentificacion())) {
    		          valido = true;
    		          break;
    	         }
            }
            
            if(valido.equals(Boolean.TRUE)) {
            	
            	if(usuarioValidar == null) {
            	           	
	            	usuarioRepository.save(Usuario.builder()
	            			.nombres(usuario.getNombres())
	            			.apellidos(usuario.getAPellidos())
	            			.tipoIdentificacion(usuario.getTipoIdentificacion())
	            			.numeroIdentificacion(usuario.getNumeroIdentificacion())
	            			.direccion(usuario.getDireccion())
	            			.telefono(usuario.getTelefono())
	            			.email(usuario.getEmail())
	    	                .password(this.passwordEncoder.encode(usuario.getPassword()))
	    	                .roles(Arrays.asList( "ROLE_USER"))
	    	                .build());
	               
	               response.put(Constantes.CODIGO_HTTP, "200");
	               response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
            	}
            	else {
            		 response.put(Constantes.CODIGO_HTTP, "500");
                     response.put(Constantes.MENSAJE_EXITO, "El tipo de Identificación ya está registrado, por favor ingresa otro número de identificación.");
            	}
            }
            else {
    	       response.put(Constantes.CODIGO_HTTP, "500");
               response.put(Constantes.MENSAJE_EXITO, "El tipo de Identificación no es valido.");
            }

            return response;
        } catch (ParseException e) {
            response.put(Constantes.CODIGO_HTTP, "500");
            response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
            return response;
        }
       
    }
	
	//Buscar por Id usuario
	@GetMapping("/agenciainmobiliaria/usuario/{id}")
	public UsuarioDTO buscarUsuario(@PathVariable Long id) {
		
		UsuarioDTO usuarioDTO = null;
		
		try {
			usuarioDTO = convertUsuario.usuarioConvertToDTO(usuarioRepository.findById(id).get());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return usuarioDTO;
	}
	
	
	@GetMapping("/agenciainmobiliaria/usuarios")
	public Iterable<UsuarioDTO> listarUsuarios(){
		
		Iterable<UsuarioDTO> usuarioDTO = null;
		
		try {
			usuarioDTO = convertUsuario.listConvertToDTO(usuarioRepository.findAll());
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
		return usuarioDTO;
			
	}
	
	@PutMapping("/agenciainmobiliaria/usuario/{id}")
	public ResponseEntity<Object> updateUsuario(@RequestBody UsuarioDTO newUser, @PathVariable Long id){
		if(usuarioRepository.findById(id).isPresent()) {
			Usuario user = usuarioRepository.findById(id).get();
			user.setNombres(newUser.getNombres());
			user.setAPellidos(newUser.getAPellidos());
			user.setTipoIdentificacion(newUser.getTipoIdentificacion());
			user.setNumeroIdentificacion(newUser.getNumeroIdentificacion());
			user.setDireccion(newUser.getDireccion());
			user.setTelefono(newUser.getTelefono());
			user.setEmail(newUser.getEmail());
			user.setPassword(newUser.getPassword());
			
			usuarioRepository.save(user);
			
			return ResponseEntity.ok("Se actualizó exitosamente");
			
		}
        else {
             return null;
        }
		
	}
	
	@DeleteMapping("/agenciainmobiliaria/usuario/{id}")
    public Map<String, String> eliminarUsuario(@PathVariable Long id) {
		
		Map<String, String> response = new HashMap<>();
		try {
    	     usuarioRepository.deleteById(id);
    	     response.put(Constantes.CODIGO_HTTP, "200");
    	     response.put(Constantes.MENSAJE_EXITO, "El Usuario eliminado exitosamente");
		}
		catch (Exception e) {
			response.put(Constantes.CODIGO_HTTP, "500");
	        response.put(Constantes.MENSAJE_EXITO, "Se presento un error al eliminar el usuario ó el Usuario con el Id "+id+" no existe.");
		}
    	
		return response;
    	
    }

	
}
