package com.NoCountry.PetHealthTracker.model.dto;

import java.time.LocalDateTime;

public record EventoSaludRequest(String tipo, LocalDateTime fechaProgramada,
                                 String notas,String veterinario,
                                 Long mascota,Long medicamento,Long usuario) {
}
