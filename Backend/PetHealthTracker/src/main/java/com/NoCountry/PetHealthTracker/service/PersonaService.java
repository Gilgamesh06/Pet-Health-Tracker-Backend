package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.auth.dto.RegisterDTO;
import com.NoCountry.PetHealthTracker.model.entity.Persona;
import com.NoCountry.PetHealthTracker.repository.PersonaRepository;
import com.NoCountry.PetHealthTracker.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository,
                          UsuarioRepository usuarioRepository){
        this.personaRepository = personaRepository;
    }

    protected Persona createObjectPersona(RegisterDTO register){
        return Persona.builder()
                .nombre(register.getNombre())
                .apellido(register.getApellido())
                .fechaNacimiento(register.getFechaNacimiento())
                .build();
    }

    public Persona savePersona(RegisterDTO register){
        Persona persona =  createObjectPersona(register);
        return personaRepository.save(persona);
    }
}
