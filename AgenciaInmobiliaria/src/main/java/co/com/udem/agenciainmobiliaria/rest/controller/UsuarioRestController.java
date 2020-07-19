package co.com.udem.agenciainmobiliaria.rest.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	
	@PostMapping("/agenciainmobiliaria/adicionarUsuario")
    public Map<String, String> adicionarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Usuario usuario = convertUsuario.convertToEntity(usuarioDTO);
          
   	        Iterable<TiposDeIdentificacion> iTiposIdentificacion = tiposDeIdentificacionRepository.findAll();
            List<TiposDeIdentificacion> listaTiposIdentificacion = new ArrayList<TiposDeIdentificacion>();
            iTiposIdentificacion.iterator().forEachRemaining(listaTiposIdentificacion::add);
     
            Boolean valido = false;
            for(int i = 0; i < listaTiposIdentificacion.size(); i++) {            	 
    	         if(usuario.getTipoIdentificacion().equals(listaTiposIdentificacion.get(i).getTipoIdentificacion())) {
    		          valido = true;
    		          break;
    	         }
            }

            if(valido==true) {
               usuarioRepository.save(usuario);
               response.put(Constantes.CODIGO_HTTP, "200");
               response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
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
	
	@GetMapping("/agenciainmobiliaria/usuario/{id}")
	public Usuario buscarUsuario(@PathVariable Long id) {
		return usuarioRepository.findById(id).get();
	}
	
	
	@GetMapping("/agenciainmobiliaria/usuarios")
	public Iterable<UsuarioDTO> listarUsuarios(){
		
		Iterable<UsuarioDTO> usuarioDTO = null;
		
		try {
			usuarioDTO = convertUsuario.listConvertToDTO(usuarioRepository.findAll());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return usuarioDTO;
			
	}
	
	@PutMapping("/agenciainmobiliaria/usuario/{id}")
	public ResponseEntity<Object> updateUsuario(@RequestBody Usuario newUser, @PathVariable Long id){
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
    public void eliminarUsuario(@PathVariable Long id) {
    	usuarioRepository.deleteById(id);
    }

	
}
