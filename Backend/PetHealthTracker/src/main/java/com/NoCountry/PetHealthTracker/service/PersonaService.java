package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.auth.dto.RegisterDTO;
import com.NoCountry.PetHealthTracker.model.dto.MessageDTO;
import com.NoCountry.PetHealthTracker.model.dto.UpdatePersonaDTO;
import com.NoCountry.PetHealthTracker.model.dto.UserInfoDTO;
import com.NoCountry.PetHealthTracker.model.entity.Persona;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final UsuarioService usuarioService;

    public PersonaService(PersonaRepository personaRepository,
                          UsuarioService usuarioService) {
        this.personaRepository = personaRepository;
        this.usuarioService = usuarioService;
    }

    /**
     * Metodo que crea un Objeto Persona a partir del DTO RegisterDTO
     * @param register DTO -> Contiene los datos de registro
     * @return Persona
     */
    protected Persona createObjectPersona(RegisterDTO register) {
        return Persona.builder()
                .nombre(register.getNombre())
                .apellido(register.getApellido())
                .fechaNacimiento(register.getFechaNacimiento())
                .build();
    }

    /**
     * Metodo que guarda una Persona en la DB
     * @param register DTO
     * @return Persona  -> guardada
     */
    public Persona savePersona(RegisterDTO register) {
        Persona persona = createObjectPersona(register);
        return personaRepository.save(persona);
    }

    /**
     * Metodo que calcula la edad
     * @param fechaNacimiento
     * @return Long con la edad
     */
    public Long calculetedAge(LocalDate fechaNacimiento){
        LocalDate fechaActual = LocalDate.now();
        // Calcula el periodo de tiempo en años entre la fecha actual (sistema) y la del usuario fechaNacimiento
        int edad = Period.between(fechaNacimiento, fechaActual).getYears();
        return (long) edad;
    }


    /**
     * Metodo que Crea un DTO -> UserInfoDTO
     * @param persona
     * @param email
     * @return UserInfoDTO
     */
    private UserInfoDTO createUserInfo(Persona persona, String email){
        return UserInfoDTO.builder()
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .edad(calculetedAge(persona.getFechaNacimiento()))
                .email(email)
                .build();
    }

    /**
     * Metod que valida si el String no es nulo y no esta vacio
     * @param value
     * @return boolean
     */
    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Metodo que actualiza los datos de Perona
     * @param persona
     * @param updatePersona DTO
     * @return Persona -> pueda haber sido actualizada o no
     */
    private Persona updatePerson(Persona persona, UpdatePersonaDTO updatePersona) {
        // verifica si se ha hecho alguna actualización
        boolean updated = false;
        LocalDateTime now = LocalDateTime.now();

        if (isNotEmpty(updatePersona.getNombre())) {
            persona.setNombre(updatePersona.getNombre());
            updated = true;
        }
        if (isNotEmpty(updatePersona.getApellido())) {
            persona.setApellido(updatePersona.getApellido());
            updated = true;
        }
        if (updatePersona.getFechaNacimiento() != null) {
            persona.setFechaNacimiento(updatePersona.getFechaNacimiento());
            updated = true;
        }
        if (updated) {
            persona.setFechaActualizacion(now); // Establece la fecha de actualización si hay cambios
        }

        return persona;
    }

    /**
     * Metodo que actualiza una persona
     * @param updatePersona DTO
     * @return UserInfoDTO
     */
    public UserInfoDTO updatePersona(UpdatePersonaDTO updatePersona){

        // Obtiene el usuario del contexto
        Usuario user = usuarioService.getUserAuthenticated();

        // Actualiza los valores
        Persona person = updatePerson(user.getPersona(), updatePersona);

         Persona persona = personaRepository.save(person);

        return createUserInfo(persona, user.getEmail());

    }

    /**
     * Metodo que retorna los datos del user
     * @return UserInfoDTO
     */
    public UserInfoDTO getUser(){
        // Obtiene el usuario del contexto
        Usuario user = usuarioService.getUserAuthenticated();

        // Obtiene la persona logeada
        Persona persona = user.getPersona();
        return createUserInfo(persona, user.getEmail());
    }


    /**
     * Metodo para eliminar un usuario
     * @return MessageDTO
     */
    public MessageDTO delete(){

        String estado = "INACTIVO";
        LocalDateTime now = LocalDateTime.now();

        // Obtiene el usuario del contexto
        Usuario user = usuarioService.getUserAuthenticated();
        user.setEstado(estado);
        user.setFechaEliminacion(now);
        Persona persona = user.getPersona();
        persona.setEstado(estado);
        persona.setFechaEliminacion(now);

        return  MessageDTO.builder()
                .message("Usuario:" +  persona.getNombre() + persona.getApellido() + " eliminado exitosamente.")
                .build();
    }



}
