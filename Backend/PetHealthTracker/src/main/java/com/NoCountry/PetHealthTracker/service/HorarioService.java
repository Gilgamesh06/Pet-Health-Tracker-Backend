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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService {
    private final HorarioRepository horarioRepository;
    private final MascotaRepository mascotaRepository;
    private final ComidaRepository comidaRepository;


    /**
     * Este metodo retornara todos los registros de la base de datos
     * @return lista de {@link HorarioResponse}
     */
    public List<HorarioResponse> getAll(){

        return horarioRepository.findAll()
                .stream()
                .map(this::horarioToResponse)
                .toList();
    }


    /**
     * Este metodo servira para guardar un nuevo horario en la bd
     *
     * @param request trae los datos a guardar en la bd en un dto {@link HorarioRequest}
     * @return dto de {@link  HorarioResponse} con los datos guardados en la bd
     */
    public HorarioResponse save(HorarioRequest request) {

        Mascota mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro una mascota con el id " + request.getIdMascota() + " para vincular en el horario"));

        Comida comida = comidaRepository.findById(request.getIdComida())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro una comida con el id " + request.getIdComida() + " para vincular en el horario"));

        Horario horario = requestToHorario(request);
        horario.setMascota(mascota);
        horario.setComida(comida);

        return horarioToResponse(horarioRepository.save(horario));
    }


    /**
     * Con este metodo buscaremos un registro de horario en la base de datos mediante un id
     *
     * @param id es el identificador con el que buscaremos en la base de datos
     * @return {@link HorarioResponse} dto de {@link Horario} con los datos encontrados
     */
    public HorarioResponse findById(Long id) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ah encontrado registro con el id " + id));
        return horarioToResponse(horario);
    }


    /**
     * Con este metodo actualizaremos un registro de la base de dato
     *
     * @param id      identificador para encontrar el registro actualizar
     * @param request trae los datos nuevos
     * @return {@link HorarioResponse} dto {@link Horario}
     */
    @Transactional
    public HorarioResponse updateById(Long id, HorarioRequest request) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro registro de horario con el id " + id));

        horario.setHora(request.getHora());
        horario.setCantidad(request.getCantidad());
        horario.setDiaSemana(request.getDiaSemana());
        horario.setRecordatorio(request.getRecordatorio());

        return horarioToResponse(horarioRepository.save(horario));
    }

    /**
     * Este servicio desactivara un registro (eliminacion logica)
     * @param id identificador para ubicar el registro a desactivar
     * @return true si tod* sale bien en caso de que no una exepcion
     */
    @Transactional
    public Boolean deleteById(Long id){

        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro registro horario con el id " + id));

        horario.setEstado("INACTIVO");
        horario.setFechaEliminacion(LocalDateTime.now());
        horarioRepository.save(horario);

        return true;
    }

    /**
     *
     * @param horario
     * @return
     */
    private HorarioResponse horarioToResponse(Horario horario) {
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

    private Horario requestToHorario(HorarioRequest request) {
        Mascota mascota = mascotaRepository.findById(request.getIdMascota()).orElseThrow(() -> new EntityNotFoundException("No se encontro registro de mascota con el id " + request.getIdMascota()));
        Comida comida = comidaRepository.findById((request.getIdComida())).orElseThrow(() -> new EntityNotFoundException("No se encontro registro de comida con el id " + request.getIdComida()));
        return Horario.builder()
                .hora(request.getHora())
                .cantidad(request.getCantidad())
                .diaSemana(request.getDiaSemana())
                .recordatorio(request.getRecordatorio())
                .mascota(mascota)
                .comida(comida)
                .build();
    }

    public Horario resposeToHorario(HorarioResponse response) {
        Mascota mascota = mascotaRepository.findById(response.getIdMascota()).orElseThrow(() -> new EntityNotFoundException("No se encontro registro de mascota con el id " + response.getIdMascota()));
        Comida comida = comidaRepository.findById((response.getIdComida())).orElseThrow(() -> new EntityNotFoundException("No se encontro registro de comida con el id " + response.getIdComida()));
        return Horario.builder()
                .hora(response.getHora())
                .cantidad(response.getCantidad())
                .diaSemana(response.getDiaSemana())
                .recordatorio(response.getRecordatorio())
                .mascota(mascota)
                .comida(comida)
                .build();
    }
}
