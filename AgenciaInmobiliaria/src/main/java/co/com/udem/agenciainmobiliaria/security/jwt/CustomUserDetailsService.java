package co.com.udem.agenciainmobiliaria.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import co.com.udem.agenciainmobiliaria.repositories.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository users;

    public CustomUserDetailsService(UsuarioRepository users) {
        this.users = users;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.users.findByNumeroIdentificacion(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
    
   /* @Override
    public UserDetails loadUserByUsername(String numeroIdentificacion) throws UsernameNotFoundException {
        return (UserDetails) this.users.findByNumeroIdentificacion(numeroIdentificacion)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + numeroIdentificacion + " not found"));
    }*/


}
