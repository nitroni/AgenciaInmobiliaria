package co.com.udem.agenciainmobiliaria.util;

import java.text.ParseException;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.agenciainmobiliaria.dto.TiposDeIdentificacionDTO;

import co.com.udem.agenciainmobiliaria.entities.TiposDeIdentificacion;


public class ConvertTiposDeIdentificacion {
	
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	   
    public TiposDeIdentificacion convertToEntity(TiposDeIdentificacionDTO tiposDeIdentificacionDTO) throws ParseException {
        return modelMapper.map(tiposDeIdentificacionDTO, TiposDeIdentificacion.class);
    }

    public TiposDeIdentificacionDTO tiposIdentificacionvertToDTO(TiposDeIdentificacion tiposDeIdentificacion) throws ParseException {
        return modelMapper.map(tiposDeIdentificacion, TiposDeIdentificacionDTO.class);
    }

    public Iterable<TiposDeIdentificacionDTO> listConvertToDTO(Iterable<TiposDeIdentificacion> entity) throws ParseException{                     
        return Arrays.asList(modelMapper.map(entity, TiposDeIdentificacionDTO[].class));
    }
	

}
