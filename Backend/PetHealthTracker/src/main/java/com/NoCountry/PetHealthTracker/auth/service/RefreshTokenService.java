package com.NoCountry.PetHealthTracker.auth.service;

import com.NoCountry.PetHealthTracker.auth.dto.TokenDTO;
import com.NoCountry.PetHealthTracker.auth.dto.TokenRefreshRequest;
import com.NoCountry.PetHealthTracker.model.entity.RefreshToken;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.RefreshTokenRepository;
import com.NoCountry.PetHealthTracker.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class RefreshTokenService {

    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;



    public RefreshTokenService(UsuarioRepository usuarioRepository,
                               RefreshTokenRepository refreshTokenRepository,
                               JwtService jwtService,
                               CustomUserDetailsService customUserDetailsService){
        this.usuarioRepository = usuarioRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Almacena en la DB el token con el respectivo usuario
     * @param email
     * @param token
     * @param expiration
     * @return RefreshToken
     */
    public RefreshToken createRefreshToken(String email, String token, Date expiration){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        RefreshToken refresh = RefreshToken.builder()
                .token(token)
                .fechaExpiracion(expiration)
                .usuario(usuario)
                .build();
        return refreshTokenRepository.save(refresh);
    }

    /**
     * Metodo para buscar un RefreshToken por medio del token
     * @param token
     * @return RefreshToken
     */
    public RefreshToken findByToken(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token no valido."));
    }

    /**
     * Metodo para revocar Token invalida el token sin eliminarlo
     * @param token
     * @param change
     */
    public void setTokenRevocado(String token, boolean change){
        RefreshToken refreshToken = findByToken(token);
        refreshToken.setTokenRevocado(change);
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * Metodo que valida el tiempo de expiracion del token
     * @param refreshToken
     * @return Boolean  retorna true si expiro -> false en caso contrario
     */
    public Boolean isExpired(RefreshToken refreshToken){
        return refreshToken.getFechaExpiracion().before(new Date());
    }

    /**
     * Metodo que permite validar si el token expiro y si fue revocado
     * @param refreshToken
     * @return Boolean retorna true si el token es valido, false si no
     */
    public Boolean isValid(RefreshToken refreshToken){
        return ( !isExpired(refreshToken) && !refreshToken.getTokenRevocado());
    }


    /**
     * Metodo que genera un nuevoAccessToken por medio de RefreshToken
     * @param request DTO que contiene el refreshToken
     * @return TokenDTO que contiene el token de acceso y el token de refresh
     */
    public TokenDTO generateNewAccessToken(TokenRefreshRequest request){

        String requestRefreshToken = request.getRefreshToken();

        RefreshToken refreshToken = findByToken(requestRefreshToken);

        // Extrae el claim puede ser refresh o access
        String tokenType = jwtService.extractClaim(refreshToken.getToken(), claims -> claims.get("type", String.class));

        if(isValid(refreshToken) && "refresh".equals(tokenType)){

            Usuario usuario = refreshToken.getUsuario();
            UserDetails user = customUserDetailsService.loadUserByUsername(usuario.getEmail());
            String newAccessToken = jwtService.generateAccessToken(user);
            return new TokenDTO(newAccessToken, requestRefreshToken);

        }else{
            throw new RuntimeException("Token no valido.");
        }
    }
}
