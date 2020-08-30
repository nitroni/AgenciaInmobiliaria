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
import co.com.udem.agenciainmobiliaria.dto.PropiedadDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes =AgenciaInmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropiedadRestControllerTest {

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
    public void adicionarPropiedadTest() {
    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	
    	propiedadDTO.setAreaMetrosCuadros(3.4);
    	propiedadDTO.setNumeroHabitaciones(5);
    	propiedadDTO.setNumeroBanos(4);
    	propiedadDTO.setTipoRegistro("Arriendo");
    	propiedadDTO.setValor(10000);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(propiedadDTO, headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/adicionarPropiedad", HttpMethod.POST, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    
    @Test
    public void filtoPropiedadTest() {
    	//Vamos a filtrar por el valor
    	Long parametro = (long) 10000;
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/filtropropiedad/"+parametro, HttpMethod.GET, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    @Test
    public void filtoPropiedadDosTest() {
    	//Vamos a filtrar por el valor y por el número de habitaciones
    	Long parametro1 = (long) 10000;
    	Long parametro2 = (long) 5;
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/filtropropiedad/"+parametro1+"/"+parametro2, HttpMethod.GET, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    @Test
    public void filtoPropiedadTresTest() {
    	//Vamos a filtrar por el valor, número de habitaciones y el area
    	Long parametro1 = (long) 10000;
    	Long parametro2 = (long) 5;
    	Long parametro3 = (long) 3.4;
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/filtropropiedad/"+parametro1+"/"+parametro2+"/"+parametro3, HttpMethod.GET, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    @Test
    public void updatePropiedadTest() {
    	//Modificamos el ID 1 que fue la prueba de la propiedad que se registro anteriormente
    	Long id = (long) 2;
    	adicionarPropiedadTest();
        PropiedadDTO propiedadDTO = new PropiedadDTO();
    	
    	propiedadDTO.setAreaMetrosCuadros(7.4);
    	propiedadDTO.setNumeroHabitaciones(2);
    	propiedadDTO.setNumeroBanos(7);
    	propiedadDTO.setTipoRegistro("Venta");
    	propiedadDTO.setValor(110000);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(propiedadDTO, headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/propiedad/"+id, HttpMethod.PUT, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    @Test
    public void eliminarPropiedadTest() {
    	//Vamos a eliminar el registro con el ID 1
    	Long id = (long) 1;
    	adicionarPropiedadTest();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/propiedad/"+id, HttpMethod.DELETE, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    @Test
    public void listarPropiedadesPorUsuario() {
    	adicionarPropiedadTest();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/agenciainmobiliaria/propiedadesUsuario/", HttpMethod.GET, entity, String.class);       
        assertEquals(200, postResponse.getStatusCode().value());
    }
    
    private void adicionarUsuario(UsuarioDTO usuarioDTO) {
        ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/agenciainmobiliaria/adicionarUsuario", usuarioDTO, String.class);
        postResponse.getBody();
    }
	
}
