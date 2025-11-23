package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.auth.dto.RegisterDTO;
import com.NoCountry.PetHealthTracker.auth.service.RefreshTokenService;
import com.NoCountry.PetHealthTracker.model.dto.MessageDTO;
import com.NoCountry.PetHealthTracker.model.dto.UpdateEmailDTO;
import com.NoCountry.PetHealthTracker.model.dto.UpdatePasswordDTO;
import com.NoCountry.PetHealthTracker.model.entity.Persona;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RefreshTokenService refreshTokenService,
                          PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
    }
    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario no existe o es invalido"));
    }

    /**
     * Metodo que busca un usuario a partir de su Email
     * @param email correo del usuario
     * @return Optional
     */
    public Optional<Usuario> getUserByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Metodo que retorna el Email del Usuario Autenticado
     * @return String -> Email
     */
    private String getEmailUserLogin(){
        // 1. Obtener autenticación del contexto
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Obtener el email (UserDetails  atributo -> username = email )
        return authentication.getName();
    }

    /**
     * Metodo que retorna el Usuario autenticado // Crear excepcion personalizada y manaejarla Controller
     * @return Usuario
     */
    protected Usuario getUserAuthenticated(){

        String email = getEmailUserLogin();

        Optional<Usuario> usuarioOpt = getUserByEmail(email);
        if(usuarioOpt.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuarioOpt.get();
    }

    /**
     * Metodo que crea un Objeto de tipo usuario a partir de DTO y Persona
     * @param register DTO -> Email and Password
     * @param persona Object -> relacion uno a uno
     * @return Usuario
     */
    protected Usuario createObjectUsuario(RegisterDTO register, Persona persona){
        return Usuario.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .persona(persona)
                .build();
    }

    /**
     * Metodo que guarda el usaurio ingresado
     * @param register DTO -> contiene el email y la password
     * @param persona Objet -> Persona que esta vinculado al usuario
     * @return Usuario objeto creado a partir del DTO y Persona
     */
    public Usuario saveUsuario(RegisterDTO register, Persona persona){

        Optional<Usuario> usuarioOpt = getUserByEmail(register.getEmail());
        if(usuarioOpt.isEmpty()){
            Usuario usuario = createObjectUsuario(register, persona);
            return usuarioRepository.save(usuario);
        }else{
            throw new RuntimeException("Usuario ya registrado");
        }
    }


    /**
     * Metodo para cabiar la contraseña cuando esta logeado // nota crear excepcion personalizada y manejo
     * @param updatePassword DTO -> contiene la nueva contraseña
     * @return MessageDTO respuesta en formato JSON
     */
    public MessageDTO changePassword(UpdatePasswordDTO updatePassword){

        // Obtiene el usuario del contexto
        Usuario user = getUserAuthenticated();

        if(user.getPassword().equals(updatePassword.getPassword())){
            throw new RuntimeException("La contraseña no puede ser la misma.");
        }
        else{
            // Codifica la contraseña
            user.setPassword(passwordEncoder.encode(updatePassword.getPassword()));
            user.setFechaActualizacion(LocalDateTime.now());
            // Guarda la nueva contraseña
            Usuario usuario =  usuarioRepository.save(user);

            // Revoca los tokens de refresco
            refreshTokenService.setRevocarToken(usuario);

            return  MessageDTO.builder()
                    .message("La contraseña fue modificada exitosamente.")
                    .build();
        }
    }

    /**
     * Metodo para cambiar el Correo electronico del usaurio // Nota: Agregar excepcion personalizada y agregar manejo
     * @param updateEmail DTO -> Contiene el nuevo correo
     * @return MessageDTO respuesta en formato JSON
     */
    public MessageDTO changeEmail(UpdateEmailDTO updateEmail){

        // Obtiene el usuario del contexto
        Usuario user = getUserAuthenticated();

        // verifica si el email es el mismo
        if( user.getEmail().equals(updateEmail.getEmail())){
            throw new RuntimeException("El correo no puede ser el mismo.");
        }
        else{
            user.setEmail(updateEmail.getEmail());
            user.setFechaActualizacion(LocalDateTime.now());
            // Guarda la nueva contraseña
            Usuario usuario =  usuarioRepository.save(user);

            // Revoca los tokens de refresco
            refreshTokenService.setRevocarToken(usuario);

            return  MessageDTO.builder()
                    .message("Email:" +  usuario.getEmail() + " modificado exitosamente.")
                    .build();
        }

    }
}
