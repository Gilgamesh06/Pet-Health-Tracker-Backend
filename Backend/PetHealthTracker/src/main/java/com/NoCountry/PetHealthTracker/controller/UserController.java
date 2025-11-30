package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.*;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.service.PersonaService;
import com.NoCountry.PetHealthTracker.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UsuarioService usuarioService;
    private final PersonaService personaService;

    public UserController(UsuarioService usuarioService,
                          PersonaService personaService){
        this.usuarioService = usuarioService;
        this.personaService = personaService;
    }

    /**
     * Metodo para obtener la informacion del usuario
     * @return UserInfoDTO and StatusCode 200 OK
     */
    @GetMapping("/")
    public ResponseEntity<UserInfoDTO> getUser(){
        UserInfoDTO userInfo = personaService.getUser();
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    /**
     * Metodo para actualizar la informacion del usuario
     * @param update DTO
     * @return UserInfoDTO con StatusCode 200 OK
     */
    @PutMapping("/update/user")
    public ResponseEntity<UserInfoDTO> updateUser(@RequestBody UpdatePersonaDTO update){
        UserInfoDTO user = personaService.updatePersona(update);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Metodo que actualiza la contraseña (Usuario Logueado)
     * @param updatePassword DTO
     * @return MessageDTO and StatusCode 200 OK
     */
    @PutMapping("/update/password")
    public ResponseEntity<MessageDTO> updatePassword(@RequestBody UpdatePasswordDTO updatePassword){
        MessageDTO message = usuarioService.changePassword(updatePassword);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Metodo que actualiza el correo electronico
     * @param updateEmail DTO
     * @return MessageDTO and StatusCode 200 OK
     */
    @PutMapping("/update/email")
    public ResponseEntity<MessageDTO> updateEmail(@RequestBody UpdateEmailDTO updateEmail){
        MessageDTO message = usuarioService.changeEmail(updateEmail);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Metodo que elimina el usuario Logeado
     * @return MessageDTO  and StatusCode 200 OK
     */
    @DeleteMapping("/delete")
    public ResponseEntity<MessageDTO> deleteUser(){
        Usuario user = usuarioService.delete();
        MessageDTO message = personaService.delete(user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
