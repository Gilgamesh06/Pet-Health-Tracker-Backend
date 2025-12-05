package com.NoCountry.PetHealthTracker.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public record RecordatorioResponse (Long id,
                                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                    LocalDateTime fecha,
                                    String canal,
                                    String estado,String mensaje,Long eventoSalud,
                                    Long horario){
}
