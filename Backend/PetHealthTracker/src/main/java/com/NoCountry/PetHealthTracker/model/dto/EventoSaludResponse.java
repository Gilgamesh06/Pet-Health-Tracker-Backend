package com.NoCountry.PetHealthTracker.model.dto;

import java.time.LocalDateTime;

public record EventoSaludResponse(Long id, String tipo,
                                  LocalDateTime fechaRealizacion,LocalDateTime fechaProgramada,
                                  String notas,String veterinario,
                                  Long medicamento) {
}
