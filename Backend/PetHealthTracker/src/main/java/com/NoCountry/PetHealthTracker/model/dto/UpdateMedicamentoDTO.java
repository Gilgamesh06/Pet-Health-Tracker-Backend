package com.NoCountry.PetHealthTracker.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMedicamentoDTO {

    private String nombre;
    private String tipo;
    private String fabricante;
    private Integer intervaloDosis;
    private String descripcion;
}
