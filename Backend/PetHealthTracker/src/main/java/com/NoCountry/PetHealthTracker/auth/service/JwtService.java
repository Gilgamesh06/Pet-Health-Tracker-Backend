package com.NoCountry.PetHealthTracker.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    /**
     * Clave secreta que se importa desde aplication.properties
     */
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.time.expiration.access.key}")
    private String timeExpiration;

    @Value("${jwt.time.expiration.refresh.key}")
    private String timeExpirationRefresh;

    /**
     * Metodo que me genera un objeto de la clase Key a partir del atributo SECRET_KEY
     * @return Key: objeto de la clase Key
     */
    private Key getKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    /**
     * Metodo para generar el token
     * @param userDetails : Parametro que contiene el username, password y role del usuario
     * @return String : token Jwt para el usuario
     */
    public String generateAccessToken(UserDetails userDetails){
        return Jwts.builder() // Construye un JWT
                .setSubject(userDetails.getUsername()) // define el sujeto con el username del usuario
                .claim("type", "access")
                .setIssuedAt(new Date()) // fecha de expedicion del token
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getKey(), SignatureAlgorithm.HS256) // froma token con la key y se encrita con H256
                .compact(); // compacta en un string
    }

    public String generateRefreshToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpirationRefresh)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Metodo para extraer el cuerpo del jwt y retronarlo como un Claims
     * @param token es el token generado por el metodo generateToken es un String
     * @return Claims objeto que contiene el cuerpo del JWT
     */
    private Claims extractAllClaims(String token) throws JwtException {
        return  Jwts.parserBuilder() // Crea un nuevo constructor para el pase
                .setSigningKey((getKey())) // asigna la clave para poder verificar el token
                .build() // construye el objeto claims
                .parseClaimsJws(token) // verifica el token
                .getBody(); // obtiene el cuerpo del jwt y lo converte a claims
    }

    // Este metodo retorna un claim especifico del token por eso devuelve un objeto generico

    /**
     * Metodo para retorno de un claim especifco por medio del token y una interfaz funcional
     * @param token es el token generado por el metodo generateToklen
     * @param claimsFunction Interfaz funcional para extraer cualquie claim
     * @return claims expecifico ejemplo getSubject
     * @param <T> Objeto generico
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsFunction){
        Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }


    /**
     * Metodo para validar el token
     * @param token es el jwt generado
     * @param userDetails es un objeto que contiene el nickname y password del usuario
     * @return boolean true si el token es valido false si no es valido
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractClaim(token, Claims::getSubject);
        return (email.equals(userDetails.getUsername()));
    }
}
