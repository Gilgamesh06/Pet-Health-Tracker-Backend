package com.NoCountry.PetHealthTracker.auth.service;

import com.NoCountry.PetHealthTracker.auth.dto.LoginRequest;
import com.NoCountry.PetHealthTracker.auth.dto.RegisterRequest;
import com.NoCountry.PetHealthTracker.auth.dto.TokenDTO;
import com.NoCountry.PetHealthTracker.model.dto.MessageDTO;
import com.NoCountry.PetHealthTracker.model.entity.Persona;
import com.NoCountry.PetHealthTracker.model.entity.RefreshToken;
import com.NoCountry.PetHealthTracker.service.PersonaService;
import com.NoCountry.PetHealthTracker.service.UsuarioService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final PersonaService personaService;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(PersonaService personaService,
                       UsuarioService usuarioService,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService, RefreshTokenService refreshTokenService){
        this.personaService = personaService;
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Metodo para registar el usarioo
     * @param register DTO que contiene la informacion para registar al usaurio
     * @return retorna un MessageDTO
     */
    public MessageDTO registerUser(RegisterRequest register){
        Persona persona = personaService.savePersona(register);
        usuarioService.saveUsuario(register, persona);

        return MessageDTO.builder()
                .message("Usuario:" +  persona.getNombre() + persona.getApellido() + " registrado exitosamente.")
                .build();
    }


    /**
     * Metodo para realizar login
     * @param login DTO que contiene el email y la password del usuario
     * @return tokenDTO que contiene el token de acceso y el de refresco
     */
    public TokenDTO loginUser(LoginRequest login){

        // Aquí entra el AuthenticationProvider configurado
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
        // Si llega aquí, significa que fue autenticado con éxito
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Generar el token con el usuario autenticado
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);
        Date expiration = jwtService.extractClaim(newRefreshToken, Claims::getExpiration);
        RefreshToken RefreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername(), newRefreshToken, expiration);
        return new TokenDTO(newAccessToken,newRefreshToken);
    }
}
