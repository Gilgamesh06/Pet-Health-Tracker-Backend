package com.NoCountry.PetHealthTracker.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EventoSaludRequest(String tipo,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                 LocalDateTime fechaProgramada,
                                 String notas,
                                 String veterinario,
                                 Long mascota,
                                 Long medicamento,
                                 Long usuario) {
}
