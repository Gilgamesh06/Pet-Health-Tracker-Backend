package com.NoCountry.PetHealthTracker.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComidaRequest {

    @NotBlank
    private String nombre;
    private String descripcion;
}
