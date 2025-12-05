package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.*;
import com.NoCountry.PetHealthTracker.model.entity.EventoSalud;
import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.EventoSaludRepository;
import com.NoCountry.PetHealthTracker.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public EventoSaludPaginacion obtenerEventosSaludPorMascota(Long idMascota, LocalDate fecha,
                                                               int numeroPagina, int tamanio){
        Mascota mascota = mascotaRepository.findById(idMascota).get();
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59);
        Pageable paginacion = PageRequest.of(numeroPagina, tamanio);
        Page<EventoSalud> eventosSaludTemp = eventoSaludRepository.findByFechaProgramadaBetweenAndMascota(inicio,fin,mascota,paginacion);
        if(numeroPagina > eventosSaludTemp.getTotalPages()){
            //Arrojar una excepcion por buscar una pagina mayor a las existentes
        }
        List<EventoSaludResponse> eventosSalud = new ArrayList<>();
        for(EventoSalud eventoSalud:eventosSaludTemp.getContent()){
            eventosSalud.add(
                    new EventoSaludResponse(eventoSalud.getId(), eventoSalud.getTipo(),
                            eventoSalud.getFechaRealizacion(),eventoSalud.getFechaProgramada(),
                            eventoSalud.getNotas(), eventoSalud.getVeterinario(), eventoSalud.getMedicamento().getId())
            );
        }
        return new EventoSaludPaginacion(eventosSalud,
                new Paginacion(eventosSaludTemp.getNumber(),
                        eventosSaludTemp.getTotalElements(),
                        eventosSaludTemp.getTotalPages(),
                        eventosSaludTemp.getSize()));

    }

}
