package com.NoCountry.PetHealthTracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordatorioResponse {
    private Long id;
    private LocalDateTime fecha;
    private String canal;
    private String estado;
    private String mensaje;
    private Long idEventoSalud;
    private Long idHorario;
    private Long idUsuario;
}
