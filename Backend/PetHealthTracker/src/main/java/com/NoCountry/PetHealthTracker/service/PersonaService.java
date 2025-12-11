package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.auth.dto.RegisterRequest;
import com.NoCountry.PetHealthTracker.exception.user.UsuarioExistenteException;
import com.NoCountry.PetHealthTracker.model.dto.MessageDTO;
import com.NoCountry.PetHealthTracker.model.dto.UpdatePersonaDTO;
import com.NoCountry.PetHealthTracker.model.dto.UserInfoDTO;
import com.NoCountry.PetHealthTracker.model.entity.Persona;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.PersonaRepository;
import com.NoCountry.PetHealthTracker.service.interfaces.UpdateProcess;
import com.NoCountry.PetHealthTracker.service.utility.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
public class PersonaService implements UpdateProcess<Persona, UpdatePersonaDTO> {

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
    protected Persona createObjectPersona(RegisterRequest register) {
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
    public Persona savePersona(RegisterRequest register) {
        Optional<Usuario> usuarioOpt = usuarioService.getUserByEmail(register.getEmail());
        if(usuarioOpt.isPresent()){
            // Validamos que el usuario y persona esten activos
            String estado = "ACTIVO";
            Usuario usuario = usuarioOpt.get();
            if(usuario.getEstado().equals(estado) && usuario.getPersona().getEstado().equals(estado) ){
                throw new UsuarioExistenteException("El usuario ya existe y está activo.");
            }
            else{
                // Si existen pero no estan activos actualizamos la informacion y cambiamos el estado y fecha actualizacion.
                Persona persona = usuario.getPersona();
                persona.setNombre(register.getNombre());
                persona.setApellido(register.getApellido());
                persona.setFechaNacimiento(register.getFechaNacimiento());
                persona.setFechaActualizacion(LocalDateTime.now());
                persona.setEstado(estado);
                return personaRepository.save(persona);
            }
        }
        else{
            Persona persona = createObjectPersona(register);
            return personaRepository.save(persona);
        }
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
    private UserInfoDTO createUserInfo(Persona persona, String email) {
        return UserInfoDTO.builder()
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .edad(calculetedAge(persona.getFechaNacimiento()))
                .email(email)
                .build();
    }


    /**
     * Metodo que actualiza los datos de Perona
     * @param persona
     * @param updatePersona DTO
     * @return Persona -> pueda haber sido actualizada o no
     */
    public Persona update(Persona persona, UpdatePersonaDTO updatePersona) {
        // verifica si se ha hecho alguna actualización
        boolean updated = false;
        LocalDateTime now = LocalDateTime.now();


        if (StringUtils.isNotEmpty(updatePersona.getNombre())) {
            persona.setNombre(updatePersona.getNombre());
            updated = true;
        }
        if (StringUtils.isNotEmpty(updatePersona.getApellido())) {
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
    @Transactional
    public UserInfoDTO updatePersona(UpdatePersonaDTO updatePersona){

        // Obtiene el usuario del contexto
        Usuario user = usuarioService.getUserAuthenticated();

        // Actualiza los valores
        Persona person = update(user.getPersona(), updatePersona);

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
    @Transactional
    public MessageDTO delete(Usuario user){

        String estado = "INACTIVO";
        LocalDateTime now = LocalDateTime.now();

        Persona persona = user.getPersona();
        persona.setEstado(estado);
        persona.setFechaEliminacion(now);
        personaRepository.save(persona);

        return MessageDTO.builder()
                .message("Usuario:" +  persona.getNombre() + persona.getApellido() + " eliminado exitosamente.")
                .build();
    }



}
