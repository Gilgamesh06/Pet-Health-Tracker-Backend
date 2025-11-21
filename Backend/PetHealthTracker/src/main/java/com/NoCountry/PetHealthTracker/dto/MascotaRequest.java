package com.NoCountry.PetHealthTracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaRequest {

    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    @Positive(message = "El peso debe ser mayor a 0")
    private Double peso;
    private String foto;
    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;
}
