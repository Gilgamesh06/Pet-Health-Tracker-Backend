package com.NoCountry.PetHealthTracker.model.dto;

import com.NoCountry.PetHealthTracker.model.entity.Comida;
import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioRequest {

    private Time hora;
    private Integer cantidad;
    private Integer diaSemana;
    private Boolean recordatorio;
    private Long idMascota;
    private Long idComida;
}
