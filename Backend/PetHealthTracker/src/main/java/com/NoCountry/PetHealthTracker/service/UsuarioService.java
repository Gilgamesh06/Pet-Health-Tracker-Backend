package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.auth.dto.RegisterDTO;
import com.NoCountry.PetHealthTracker.model.entity.Persona;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PersonaService personaService,
                          PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    protected Usuario createObjectUsario(RegisterDTO register, Persona persona){
        return Usuario.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .persona(persona)
                .build();
    }

    public Usuario saveUsuario(RegisterDTO register, Persona persona){

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(register.getEmail());
        if(usuarioOpt.isEmpty()){
            Usuario usuario = createObjectUsario(register, persona);
            return usuarioRepository.save(usuario);
        }else{
            throw new RuntimeException("Usuario ya registrado");
        }
    }
}
