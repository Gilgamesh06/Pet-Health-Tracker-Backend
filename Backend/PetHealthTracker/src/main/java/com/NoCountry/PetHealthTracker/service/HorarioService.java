package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.HorarioRequest;
import com.NoCountry.PetHealthTracker.model.dto.HorarioResponse;
import com.NoCountry.PetHealthTracker.model.entity.Comida;
import com.NoCountry.PetHealthTracker.model.entity.Horario;
import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import com.NoCountry.PetHealthTracker.repository.ComidaRepository;
import com.NoCountry.PetHealthTracker.repository.HorarioRepository;
import com.NoCountry.PetHealthTracker.repository.MascotaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HorarioService {
    private final HorarioRepository horarioRepository;
    private final MascotaRepository mascotaRepository;
    private final ComidaRepository comidaRepository;


    /**
     * Este metodo servira para guardar un nuevo horario en la bd
     * @param request trae los datos a guardar en la bd en un dto {@link HorarioRequest}
     * @return dto de {@link  HorarioResponse} con los datos guardados en la bd
     */
    public HorarioResponse save(HorarioRequest request){

        Mascota mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro una mascota con el id " + request.getIdMascota() + " para vincular en el horario"));

        Comida comida = comidaRepository.findById(request.getIdComida())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro una comida con el id " + request.getIdComida() + " para vincular en el horario"));

        Horario horario = toHorario(request);
        horario.setMascota(mascota);
        horario.setComida(comida);

        return toResponse(horarioRepository.save(horario));
    }


    /**
     *
     * @param horario
     * @return
     */
    private HorarioResponse toResponse(Horario horario){
        return HorarioResponse.builder()
                .id(horario.getId())
                .hora(horario.getHora())
                .cantidad(horario.getCantidad())
                .diaSemana(horario.getDiaSemana())
                .recordatorio(horario.getRecordatorio())
                .idMascota(horario.getMascota().getId())
                .idComida(horario.getComida().getId())
                .build();
    }
    
    private Horario toHorario(HorarioRequest request){
        return Horario.builder()
                .hora(request.getHora())
                .cantidad(request.getCantidad())
                .diaSemana(request.getDiaSemana())
                .recordatorio(request.getRecordatorio())
                .mascota(null)
                .comida(null)
                .build();
    }
}
