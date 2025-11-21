package com.NoCountry.PetHealthTracker.auth.service;

import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        // Obtiene el objeto usuario a partir del nombre del usuario.
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado: "+ email));

        boolean enabled = usuario.getEstado().equalsIgnoreCase("ACTIVO");

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream()
                        .map(Usuario.Rol::name) // Se mapea el atributo name del Rol
                        .toArray(String[]::new))  // Lo convertimos en una arreglo de String[]
                .disabled(!enabled)
                .build();
    }
}
