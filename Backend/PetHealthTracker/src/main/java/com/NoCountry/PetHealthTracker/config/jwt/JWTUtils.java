package com.NoCountry.PetHealthTracker.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JWTUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    // Con este metodo vamos a generar y configurar el el token de acceso
    // con el username del usuario, establecemos fecha de creacion y la de expiration
    // y firmamos el token con hash hs256 para luego retornarlo.
    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Este metodo nos devuelve la firma hash con la vamos a generar el token
    private Key getKey() {
        /*TODO: Revisar cuando la app este corriendo (Juan)*/
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Este metodo validara si el token es valido
    private Boolean isTokenValid(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()// se construye el parse que es el que va a verificar la informacion con la firma que ya indicamos
                    .parseClaimsJws(token); // Si el token no es valido devuelve una exception

            return true;
        } catch (Exception e) {
            log.error("Error validando el token: ", e.getMessage());
            return false;
        }

    }


    // Este metodo tambien valida si el token es valido pero su funcion es extraer los datos o claim con
    // que se creo el token
    private Claims getClaimsToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error extrayendo los claims del token: ", e.getMessage());
            return null;
        }
    }

    // Este metodo retorna un claim especifico del token por eso devuelve un objeto generico
    private <T> T getClaim(String token, Function<Claims, T> claimsFunction){
        Claims claims = getClaimsToken(token);
        return claimsFunction.apply(claims);
    }

    // Este metodo retornara el username del token usando el metodo getClaim
    private String getUsernameFromToken(String token){
        // (token, (claims) -> claims.getSubject())
        return getClaim(token, Claims::getSubject);
    }


}
