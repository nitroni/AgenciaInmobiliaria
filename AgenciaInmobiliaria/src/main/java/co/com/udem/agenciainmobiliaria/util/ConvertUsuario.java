package co.com.udem.agenciainmobiliaria.util;

import java.text.ParseException;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.entities.Usuario;

public class ConvertUsuario {
	
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	   
    public Usuario convertToEntity(UsuarioDTO usuarioDTO) throws ParseException {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public UsuarioDTO usuarioConvertToDTO(Usuario usuario) throws ParseException {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public Iterable<UsuarioDTO> listConvertToDTO(Iterable<Usuario> entity) throws ParseException{                     
        return Arrays.asList(modelMapper.map(entity, UsuarioDTO[].class));
    }
	

}
