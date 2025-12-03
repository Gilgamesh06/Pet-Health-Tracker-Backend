package com.NoCountry.PetHealthTracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HorarioResponse {

    private Long id;
    private Time hora;
    private Integer cantidad;
    private Integer diaSemana;
    private Boolean recordatorio;
    private Long idMascota;
    private Long idComida;
}
