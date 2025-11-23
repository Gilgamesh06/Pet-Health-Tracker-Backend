package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.RecordatorioRequest;
import com.NoCountry.PetHealthTracker.model.entity.Recordatorio;
import com.NoCountry.PetHealthTracker.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordatorioService {
    private final RecordatorioRepository recordatorioRepository;

   public void save(RecordatorioRequest recordatorioRequest){
       var recordatorio = Recordatorio.builder()
               .fechaRecordatorio(recordatorioRequest.fechaRecordatorio())
               .canal("Notificacion")
               .mensaje("Tiene Programado un Evento con Fecha: "+recordatorioRequest.fechaRecordatorio()+
                       " de Tipo: "+recordatorioRequest.eventoSalud().getTipo()
               )
               .eventoSalud(recordatorioRequest.eventoSalud())
               .usuario(recordatorioRequest.usuario())
               .build();
       recordatorioRepository.save(recordatorio);
   }
}
