package com.NoCountry.PetHealthTracker.exception;

import com.NoCountry.PetHealthTracker.exception.medicamento.MedicamentoExistenteException;
import com.NoCountry.PetHealthTracker.exception.medicamento.MedicamentoNotFoundException;
import com.NoCountry.PetHealthTracker.exception.user.EmailEqualsException;
import com.NoCountry.PetHealthTracker.exception.user.PasswordEqualsException;
import com.NoCountry.PetHealthTracker.exception.user.UsuarioExistenteException;
import com.NoCountry.PetHealthTracker.model.dto.exception.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

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



    /**
     * Metodo que maneja la excepcion que se genera al guardar un Usuario ya existente
     * @param ex -> UsuarioExistenteException
     * @return ExceptionDTO and StatusCode 400 BAD REQUEST
     */
    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ExceptionDTO> UsuarioExistenteExceptionHandler(UsuarioExistenteException ex){

        ExceptionDTO exception = new ExceptionDTO(
          HttpStatus.BAD_REQUEST.value(),
          ex.getMessage(),
          LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

    /**
     * Metodo que maneja la excepcion que se genera al actualizar el email con el mismo email.
     * @param ex -> EmailEqualsException
     * @return ExceptionDTO and StatusCode 400 BAD REQUEST
     */
    @ExceptionHandler(EmailEqualsException.class)
    public ResponseEntity<ExceptionDTO> EmailEqualsExceptionHandler(EmailEqualsException ex){

        ExceptionDTO exception = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

    /**
     * Metodo que maneja la excepcion que se genera al actualizar la password con la misma password.
     * @param ex -> PasswordEqualsException
     * @return ExceptionDTO and StatusCode 400 BAD REQUEST
     */
    @ExceptionHandler(PasswordEqualsException.class)
    public ResponseEntity<ExceptionDTO> PasswordEqualsExceptionHandler(PasswordEqualsException ex){

        ExceptionDTO exception = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

    /**
     * Metodo que maneja la excepcion que se genera cuando no se encuentra el medicamento en la DB
     * @param ex -> MedicamentoNotFoundException
     * @return ExceptionDTO and StatusCode 404 NOT FOUND
     */
    @ExceptionHandler(MedicamentoNotFoundException.class)
    public ResponseEntity<ExceptionDTO> MedicamentoNotFoundExceptionHandler(MedicamentoNotFoundException ex){

        ExceptionDTO exception = new ExceptionDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception);
    }


    /**
     * Metodo que meaneja la excepcion que se genera cuando se ingresa un medicamento ya existente
     * @param ex -> MedicamentoExistenteException
     * @return ExceptionDTO and StatusCode 400 BAD REQUEST
     */
    @ExceptionHandler(MedicamentoExistenteException.class)
    public ResponseEntity<ExceptionDTO> MedicamentoExistenteExceptionHandler(MedicamentoExistenteException ex){

        ExceptionDTO exception = new ExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

}
