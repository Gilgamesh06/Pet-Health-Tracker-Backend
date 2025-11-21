package com.NoCountry.PetHealthTracker.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoInfoDTO {

    @NotNull(message = "El id no puede ser nulo.")
    private Long id;

    @NotBlank(message = "El nombre no puede ser vacio.")
    private String nombre;

    @NotBlank(message = "El tipo no puede ser vacio.")
    private String tipo;

    @NotBlank(message = "El fabricante no puede ser vacio.")
    private String fabricante;

    @NotNull(message = "El intervalo de dosis no puede ser nulo.")
    @Positive(message = "El intervalo de dosis debe ser positivo")
    private Integer intervaloDosis;

    @NotBlank(message = "El nombre no puede ser vacio.")
    private String descripcion;
}
