package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.MascotaRequest;
import com.NoCountry.PetHealthTracker.model.dto.MascotaResponse;
import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import com.NoCountry.PetHealthTracker.repository.MascotaRepository;
import com.NoCountry.PetHealthTracker.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MascotaService {


    /**
     * Repositorios encargados con las operaciones crud de mascota y usuario
     */
    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;


    /**
     * Obtiene todas las mascotas asociadas a un usuario.
     *
     * @param id identificador del usuario dueño de las mascotas
     * @return lista de {@link MascotaResponse} con la información de cada mascota
     */
    public List<MascotaResponse> getAll(Long id){
        return mascotaRepository.allMascotas(id)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    /**
     * Consulta los datos guardados de una mascota
     * @param id identificador de la mascota a consultar
     * @return MacostaResponse con los datos encontrados en la base de datos
     */
    public MascotaResponse getById(Long id){
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la mascota con el id " + id));
        return toResponse(mascota);
    }

    /**
     * Guarda una mascota en la base de datos
     * @param request dto con los datos necesarios para crear la mascota
     * @return MascotaRespose con los datos de la mascota guardada y su id generado por la base de datos
     * @throws EntityNotFoundException si request no tiene el id de un usuario valido en idUsuario
     * la anotacion @transational se asegura de que hibernate haga el commit si no ocurre un error
     * si no hara un rollback.
     */
    @Transactional
    public MascotaResponse save(MascotaRequest request) {


        if (request.getIdUsuario() == null) {
            throw new EntityNotFoundException("La request no tiene usuario valido");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Mascota mascota = Mascota.builder()
                .nombre(request.getNombre())
                .especie(request.getEspecie())
                .raza(request.getRaza())
                .fechaNacimiento(request.getFechaNacimiento())
                .peso(request.getPeso())
                .foto(request.getFoto())
                .usuario(usuario)
                .build();

        return toResponse(mascotaRepository.save(mascota));

    }


    /**
     * Actualizar una mascota
     * @param id es el id de la mascota actualizar
     * @param request trae los datos que se van actualizar
     * @return MascotaResponse con los datos ya actualizados
     * @throws EntityNotFoundException
     * la anotacion @transational se asegura de que hibernate haga el commit
     * si no ocurre un error si no hara un rollback.
     */
    @Transactional
    public MascotaResponse updateById(Long id, MascotaRequest request) {

        Mascota mascotaBD = mascotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro mascota con el id:" + id));

        mascotaBD.setNombre(request.getNombre());
        mascotaBD.setEspecie(request.getEspecie());
        mascotaBD.setRaza(request.getRaza());
        mascotaBD.setPeso(request.getPeso());
        mascotaBD.setFoto(request.getFoto());

        return toResponse(mascotaRepository.save(mascotaBD));
    }

    /**
     * Eliminar una mascota
     * No eliminaremos la mascota de la base de datos vamos a colocarla como inactiva solamente
     * @param id de la mascota a eliminar
     * @return Boolean para indicar si fue exitoso o no la eliminacion
     * @throws EntityNotFoundException en caso de que no se encuentre una mascota con el id indicado
     * la anotacion @transactional asegura que hibernate hago un commit automatico si la eliminacion es exitosa
     * si ocurre un error realizara u rollback
     */
    @Transactional
    public Boolean deleteById(Long id){
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NO existe mascota con el id" + id));
        mascota.setEstado("INACTIVO");
        mascota.setFechaEliminacion(LocalDateTime.now());
        mascotaRepository.save(mascota);
        return true;
    }

    /**
     * Se usa para evitar exponer la entidad principal mascota en las repuestas del api
     * @param mascota trae los datos  a mappear
     * @return MascotaResponse mapeada con el objeto mascota
     */
    private MascotaResponse toResponse(Mascota mascota) {
        return MascotaResponse.builder()
                .id(mascota.getId())
                .nombre(mascota.getNombre())
                .especie(mascota.getEspecie())
                .raza(mascota.getRaza())
                .fechaNacimiento(mascota.getFechaNacimiento())
                .peso(mascota.getPeso())
                .foto(mascota.getFoto())
                .idUsuario(mascota.getUsuario().getId())
                .build();
    }
}
