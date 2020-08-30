package co.com.udem.agenciainmobiliaria;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.com.udem.agenciainmobiliaria.entities.TiposDeIdentificacion;
import co.com.udem.agenciainmobiliaria.repositories.TiposDeIdentificacionRepository;
import co.com.udem.agenciainmobiliaria.util.ConvertPropiedad;
import co.com.udem.agenciainmobiliaria.util.ConvertTiposDeIdentificacion;
import co.com.udem.agenciainmobiliaria.util.ConvertUsuario;


@SpringBootApplication
public class AgenciaInmobiliariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciaInmobiliariaApplication.class, args);
	}

	@Bean
	public ConvertUsuario convertUsuario() {
		
		return new ConvertUsuario();
	}
	
	@Bean
	public ConvertTiposDeIdentificacion convertTiposDeIdentificacion() {
		
		return new ConvertTiposDeIdentificacion();
	}
	
	@Bean
	public ConvertPropiedad convertPropiedad() {
		
		return new ConvertPropiedad();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
	
	@Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	InitializingBean sendDatabase(TiposDeIdentificacionRepository tiposDeIdentificacionRepository) {
	       return () -> {
	    	   tiposDeIdentificacionRepository.deleteAll();
	    	   tiposDeIdentificacionRepository.save(new TiposDeIdentificacion((long)0, "CC", "Cédula de Ciudadania"));
	    	   tiposDeIdentificacionRepository.save(new TiposDeIdentificacion((long)0, "CE", "Cédula de Extrangeráa"));
	    	   tiposDeIdentificacionRepository.save(new TiposDeIdentificacion((long)0, "PA", "Pasaporte"));
	    	   tiposDeIdentificacionRepository.save(new TiposDeIdentificacion((long)0, "AS", "Adulto sin Identidad"));
	         };
	}
	       
	
}
