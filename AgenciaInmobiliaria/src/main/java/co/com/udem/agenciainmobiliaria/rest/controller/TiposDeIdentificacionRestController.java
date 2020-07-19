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
import org.springframework.web.bind.annotation.RestController;
import co.com.udem.agenciainmobiliaria.dto.TiposDeIdentificacionDTO;
import co.com.udem.agenciainmobiliaria.entities.TiposDeIdentificacion;
import co.com.udem.agenciainmobiliaria.repositories.TiposDeIdentificacionRepository;
import co.com.udem.agenciainmobiliaria.util.Constantes;
import co.com.udem.agenciainmobiliaria.util.ConvertTiposDeIdentificacion;


@RestController
public class TiposDeIdentificacionRestController {
	
	@Autowired
	private TiposDeIdentificacionRepository tiposDeIdentificacionRepository;
	
	@Autowired
	private ConvertTiposDeIdentificacion convertTiposDeIdentificacion;
	
	
	@PostMapping("/agenciainmobiliaria/adicionarTipoIdentificacion")
    public Map<String, String> adicionarTipoIdentificacion(@RequestBody TiposDeIdentificacionDTO tiposDeIdentificacionDTO) {
        Map<String, String> response = new HashMap<>();
        try {
        	TiposDeIdentificacion tiposDeIdentificacion = convertTiposDeIdentificacion.convertToEntity(tiposDeIdentificacionDTO);
            
            tiposDeIdentificacionRepository.save(tiposDeIdentificacion);
            response.put(Constantes.CODIGO_HTTP, "200");
            response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
            
            return response;
        } catch (ParseException e) {
            response.put(Constantes.CODIGO_HTTP, "500");
            response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
            return response;
        }
       
    }
	
	@GetMapping("/agenciainmobiliaria/tipoIdenficacion/{id}")
	public TiposDeIdentificacion buscarTipoIdentificacion(@PathVariable Long id) {
		return tiposDeIdentificacionRepository.findById(id).get();
	}
	
	
	@GetMapping("/agenciainmobiliaria/tiposIdentificacion")
	public Iterable<TiposDeIdentificacionDTO> listarTiposIdentificacion(){
		
	
		Iterable<TiposDeIdentificacionDTO> tiposDeIdentificacionDTO = null;
		try {
			tiposDeIdentificacionDTO = convertTiposDeIdentificacion.listConvertToDTO(tiposDeIdentificacionRepository.findAll());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return tiposDeIdentificacionDTO;
			
	}
	
	@PutMapping("/agenciainmobiliaria/tipoIdentificacion/{id}")
	public ResponseEntity<Object> updateTipoIdentificacion(@RequestBody TiposDeIdentificacion newTipo, @PathVariable Long id){
		if(tiposDeIdentificacionRepository.findById(id).isPresent()) {
			TiposDeIdentificacion tipo = tiposDeIdentificacionRepository.findById(id).get();
			
			tipo.setTipoIdentificacion(newTipo.getTipoIdentificacion());
			tipo.setTipoIdentificacion(newTipo.getDescripcion());

			tiposDeIdentificacionRepository.save(tipo);
			
			return ResponseEntity.ok("Se actualizó exitosamente");
			
		}
        else {
             return null;
        }
		
	}
	
	@DeleteMapping("/agenciainmobiliaria/tipoIdentificacion/{id}")
    public void eliminarTipoIdentificacion(@PathVariable Long id) {
		tiposDeIdentificacionRepository.deleteById(id);
    }

}
