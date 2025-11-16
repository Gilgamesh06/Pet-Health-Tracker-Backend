package com.NoCountry.PetHealthTracker.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterDTO {

    @NotBlank(message = "El nombre no puede estar vacio.")
    private String nombre;

    private String apellido;

    @NotNull(message = "La fecha de nacimiento no puede ser nula.")
    @Past(message = "La fecha debe ser anterior a la fecha actual")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo no puede ser vacio.")
    @Email(message = "El correo debe tener un formato valido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;
}