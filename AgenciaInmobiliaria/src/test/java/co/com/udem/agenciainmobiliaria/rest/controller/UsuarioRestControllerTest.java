package co.com.udem.agenciainmobiliaria.rest.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.udem.agenciainmobiliaria.AgenciaInmobiliariaApplication;
import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;



@RunWith(SpringRunner.class)
@SpringBootTest(classes =AgenciaInmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;


    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    @Test
    public void adicionarUsuarioTest() {
    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	
    	usuarioDTO.setNombres("Prueba1 Prueba2");
    	usuarioDTO.setAPellidos("Prueba3, Prueba3");
    	usuarioDTO.setTipoIdentificacion("CC");
    	usuarioDTO.setNumeroIdentificacion((long)23434456);
    	usuarioDTO.setDireccion("CR 45 nO 56 R 54");
    	usuarioDTO.setTelefono(2224354);
    	usuarioDTO.setEmail("prueba@gmail.com");
    	usuarioDTO.setPassword("1233333");
    	
    	
    	ResponseEntity<UsuarioDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/agenciainmobiliaria/adicionarUsuario", usuarioDTO, UsuarioDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }
	
}
