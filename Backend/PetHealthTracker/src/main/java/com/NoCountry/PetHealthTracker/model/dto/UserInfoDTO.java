package com.NoCountry.PetHealthTracker.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    @NotBlank(message = "El nombre no puede ser vacio.")
    private String nombre;

    private String apellido;

    @NotNull(message = "La edad no puede ser nula.")
    @Positive(message = "La edad debe ser positiva.")
    private Long edad;

    @NotBlank(message = "El correo no puede ser vacio.")
    @Email(message = "Debe tener formato de correo.")
    private String email;
}
