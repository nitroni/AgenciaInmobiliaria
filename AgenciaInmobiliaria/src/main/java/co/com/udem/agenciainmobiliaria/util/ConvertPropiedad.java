package co.com.udem.agenciainmobiliaria.util;

import java.text.ParseException;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.agenciainmobiliaria.dto.PropiedadDTO;
import co.com.udem.agenciainmobiliaria.entities.Propiedad;

public class ConvertPropiedad {
	
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	   
    public Propiedad convertToEntity(PropiedadDTO propiedadDTO) throws ParseException {
        return modelMapper.map(propiedadDTO, Propiedad.class);
    }

    public PropiedadDTO ConvertToDTO(Propiedad propiedad) throws ParseException {
        return modelMapper.map(propiedad, PropiedadDTO.class);
    }

    public Iterable<PropiedadDTO> listConvertToDTO(Iterable<Propiedad> entity) throws ParseException{                     
        return Arrays.asList(modelMapper.map(entity, PropiedadDTO[].class));
    }

}
