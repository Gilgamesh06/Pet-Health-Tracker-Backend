package com.NoCountry.PetHealthTracker.model.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonaDTO {

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
}

