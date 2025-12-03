package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.EventoSaludRequest;
import com.NoCountry.PetHealthTracker.model.dto.RecordatorioRequest;
import com.NoCountry.PetHealthTracker.model.entity.EventoSalud;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.EventoSaludRepository;
import com.NoCountry.PetHealthTracker.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoSaludService {
    private final EventoSaludRepository eventoSaludRepository;
    private final RecordatorioService recordatorioService;
    private final UsuarioService usuarioService;
    private final MascotaRepository mascotaRepository;


    private void save(EventoSaludRequest eventoSaludRequest){
        Usuario usuario = usuarioService.findById(eventoSaludRequest.usuario());
        var eventoSalud = EventoSalud.builder()
                .tipo(eventoSaludRequest.tipo())
                .fechaProgramada(eventoSaludRequest.fechaProgramada())
                .notas(eventoSaludRequest.notas())
                .veterinario(eventoSaludRequest.veterinario())
                .build();
        var eventoSaludTemp = eventoSaludRepository.save(eventoSalud);
        recordatorioService.save(new RecordatorioRequest(
                eventoSaludTemp.getFechaProgramada(),"",eventoSaludTemp,usuario));


    }
}
