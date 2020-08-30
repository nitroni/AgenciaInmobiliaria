package co.com.udem.agenciainmobiliaria.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import co.com.udem.agenciainmobiliaria.AgenciaInmobiliariaApplication;
import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.dto.AutenticationRequestDTO;
import co.com.udem.agenciainmobiliaria.dto.AutenticationResponseDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =AgenciaInmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    
    private String token;
    
    private AutenticationRequestDTO autenticationRequestDTO = new AutenticationRequestDTO();

    private UsuarioDTO usuarioDTO = new UsuarioDTO();
    
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    
    @Before
    public void authorization() {
        autenticationRequestDTO.setUsername("4444448");
        autenticationRequestDTO.setPassword("h123456");
        
        usuarioDTO.setNombres("Prueba1 Prueba2");
    	usuarioDTO.setAPellidos("Prueba3, Prueba3");
    	usuarioDTO.setTipoIdentificacion("CC");
    	usuarioDTO.setNumeroIdentificacion("4444448");
    	usuarioDTO.setDireccion("CR 45 nO 56 R 54");
    	usuarioDTO.setTelefono(2224354);
    	usuarioDTO.setEmail("prueba@gmail.com");
    	usuarioDTO.setPassword("h123456");
        
        adicionarUsuario(usuarioDTO);
        ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/auth/signin", autenticationRequestDTO, String.class);
        Gson g = new Gson();
        AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(), AutenticationResponseDTO.class);
        token = autenticationResponseDTO.getToken();
    }
    
    
    @Test
    public void adicionarUsuarioTest() {
    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	
    	usuarioDTO.setNombres("Prueba1 Prueba2");
    	usuarioDTO.setAPellidos("Prueba3, Prueba3");
    	usuarioDTO.setTipoIdentificacion("CC");
    	usuarioDTO.setNumeroIdentificacion("23434456");
    	usuarioDTO.setDireccion("CR 45 nO 56 R 54");
    	usuarioDTO.setTelefono(2224354);
    	usuarioDTO.setEmail("prueba@gmail.com");
    	usuarioDTO.setPassword("123456");
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<UsuarioDTO> entity = new HttpEntity<UsuarioDTO>(usuarioDTO, headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/adicionarUsuario", HttpMethod.POST, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    private void adicionarUsuario(UsuarioDTO usuarioDTO) {
        ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/agenciainmobiliaria/adicionarUsuario", usuarioDTO, String.class);
        postResponse.getBody();
    }
    
    @Test
    public void buscarUsuarioTest() {
    	//Vamos a buscar el primer registro insertado en la base de datos
    	Long id = (long) 1;
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/usuario/"+id, HttpMethod.GET, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    @Test
    public void updateUsuarioTest() {
    	//Modificamos el ID 1 que fue el primer registro en base de datos para la utenticación
    	Long id = (long) 2;
    	usuarioDTO.setNombres("Prueba1 Mod1");
    	usuarioDTO.setAPellidos("Prueba3, Mod3");
    	usuarioDTO.setTipoIdentificacion("CC");
    	usuarioDTO.setNumeroIdentificacion("44444489");
    	usuarioDTO.setDireccion("CR 45 nO 56 R 54");
    	usuarioDTO.setTelefono(2224354);
    	usuarioDTO.setEmail("prueba@gmail.com");
    	usuarioDTO.setPassword("h1234569");
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<UsuarioDTO> entity = new HttpEntity<UsuarioDTO>(usuarioDTO, headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/usuario/"+id, HttpMethod.PUT, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    
    @Test
    public void eliminarUsuario() {
    	//Vamos a eliminar el registro con el ID 2
    	Long id = (long) 2;
    	adicionarUsuarioTest();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/usuario/"+id, HttpMethod.DELETE, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
	
}
