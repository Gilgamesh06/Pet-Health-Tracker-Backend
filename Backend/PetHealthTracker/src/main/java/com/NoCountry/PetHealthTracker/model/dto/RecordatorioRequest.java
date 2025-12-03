package com.NoCountry.PetHealthTracker.model.dto;

import com.NoCountry.PetHealthTracker.model.entity.EventoSalud;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;

import java.time.LocalDateTime;

public record RecordatorioRequest(LocalDateTime fechaRecordatorio, String canal, EventoSalud eventoSalud,
                                  Usuario usuario) {

}
