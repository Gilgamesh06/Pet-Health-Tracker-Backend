package com.NoCountry.PetHealthTracker.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerPetHealth {

    /**
     * Manejamos las excepcione de tipo {@link EntityNotFoundException} lanzadas de cualquier controlador
     * @param e contiene el mensaje de error
     * @return ResponseEntity con estado http 400 y el mesaje de error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> badRequest(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


}
