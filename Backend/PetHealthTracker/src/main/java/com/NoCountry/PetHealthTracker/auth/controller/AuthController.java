package com.NoCountry.PetHealthTracker.auth.controller;

import com.NoCountry.PetHealthTracker.auth.dto.LoginDTO;
import com.NoCountry.PetHealthTracker.auth.dto.RegisterDTO;
import com.NoCountry.PetHealthTracker.auth.dto.TokenDTO;
import com.NoCountry.PetHealthTracker.auth.dto.TokenRefreshRequest;
import com.NoCountry.PetHealthTracker.auth.service.AuthService;
import com.NoCountry.PetHealthTracker.auth.service.RefreshTokenService;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService,
                          RefreshTokenService refreshTokenService){
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Metodo para registrar al usuario
     * @param register DTO: que contiene la informacion para registrar al usaurio
     * @return retorna por el momento el usuario y un statuscode 201
     */
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@Valid @RequestBody RegisterDTO register){
        Usuario usuario = authService.registerUser(register);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    /**
     * Metodo para logearse
     * @param login DTO que contiene el Email y Password del usaurio
     * @return retorna el DTO TokenDTO que contiene el token de aceso y el de refresh
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO login){
        TokenDTO tokenDTO = authService.loginUser(login);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    /**
     * Metodo para obtener el token de acceso por medio del token de refresco
     * @param request DTO que contiene el token de refresco
     * @return tokenDTO -> contiene el token de aceso y el de refresco
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenRefreshRequest request) {

        TokenDTO tokenDTO = refreshTokenService.generateNewAccessToken(request);
        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

}
