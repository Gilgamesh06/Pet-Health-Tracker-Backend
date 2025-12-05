package com.NoCountry.PetHealthTracker.model.dto;

import com.NoCountry.PetHealthTracker.model.entity.EventoSalud;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RecordatorioRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime fechaRecordatorio,
        String canal,
        EventoSalud eventoSalud,
        Usuario usuario) {

}
