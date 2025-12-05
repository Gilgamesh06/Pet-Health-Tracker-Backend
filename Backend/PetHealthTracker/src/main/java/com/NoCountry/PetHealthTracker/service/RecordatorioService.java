package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.Paginacion;
import com.NoCountry.PetHealthTracker.model.dto.RecordatorioPaginado;
import com.NoCountry.PetHealthTracker.model.dto.RecordatorioRequest;
import com.NoCountry.PetHealthTracker.model.dto.RecordatorioResponse;
import com.NoCountry.PetHealthTracker.model.entity.EventoSalud;
import com.NoCountry.PetHealthTracker.model.entity.Horario;
import com.NoCountry.PetHealthTracker.model.entity.Recordatorio;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.EventoSaludRepository;
import com.NoCountry.PetHealthTracker.repository.HorarioRepository;
import com.NoCountry.PetHealthTracker.repository.RecordatorioRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RecordatorioService {
    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioService usuarioService;
    private final EventoSaludRepository eventoSaludRepository;
    private final HorarioRepository horarioRepository;

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
   public RecordatorioPaginado obtenerRecordatorios(Long usuario, LocalDate fecha,
                                                    int numeroPagina, int tamanio){
       Usuario usuarioActual = usuarioService.findById(usuario);
       LocalDateTime inicio = fecha.atStartOfDay();
       LocalDateTime fin = fecha.atTime(23, 59, 59);
       Pageable paginacion = PageRequest.of(numeroPagina, tamanio);
       Page<Recordatorio>recordatoriosTemp = recordatorioRepository.findByFechaRecordatorioBetweenAndUsuario(inicio,fin,usuarioActual,paginacion);
       if(numeroPagina > recordatoriosTemp.getTotalPages()){
           //Arrojar una excepcion por buscar una pagina mayor a las existentes
       }
       List<RecordatorioResponse>recordatoriosObtenidos = new ArrayList<>();
       for(Recordatorio recordatorioTemp:recordatoriosTemp.getContent()){
           EventoSalud eventoSalud = eventoSaludRepository.findById(recordatorioTemp.getEventoSalud().getId()).get();
           Horario horario = horarioRepository.findById(recordatorioTemp.getHorario().getId()).get();
           recordatoriosObtenidos.add(new RecordatorioResponse(recordatorioTemp.getId(),recordatorioTemp.getFechaRecordatorio(),
                   recordatorioTemp.getCanal(),recordatorioTemp.getEstadoRecordatorio(),
                   recordatorioTemp.getMensaje(), eventoSalud.getId(), horario.getId()));
       }
        return new RecordatorioPaginado(recordatoriosObtenidos,
       new Paginacion(recordatoriosTemp.getNumber(),
               recordatoriosTemp.getTotalElements(),
               recordatoriosTemp.getTotalPages(),
               recordatoriosTemp.getSize()));
   }

}
