package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.ComidaRequest;
import com.NoCountry.PetHealthTracker.model.dto.ComidaResponse;
import com.NoCountry.PetHealthTracker.model.entity.Comida;
import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import com.NoCountry.PetHealthTracker.repository.ComidaRepository;
import com.NoCountry.PetHealthTracker.repository.MascotaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComidaService {

    private final ComidaRepository comidaRepository;

    /**
     * Este metodo traera todas las comidas registrada para una mascota
     *
     * @return lista de comidas que le pertenecientes a la mascota del id recibido
     */
    public List<ComidaResponse> getAll() {
        return comidaRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Este metodo traera una comida registrada
     *
     * @param id identificador de la comida
     * @return ComidaResponse con los datos encontrados en la base de datos
     * @throws IllegalArgumentException si no se encuentra la comida registrada en la base de datos
     */
    public ComidaResponse getById(Long id) {
        Comida comida = comidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe registro de comida con el id " + id));
        return toResponse(comida);
    }

    /**
     * Este metodo guardara un comida en la base de datos
     *
     * @param request trae los datos a guardar de la comida
     * @return ComidaResponse dto de comida con los datos ya guardados en la base de datos
     * la anotacion @transational se asegura de que hibernate haga el commit si todo va bien de lo contrario
     * realizara un rollback
     */
    @Transactional
    public ComidaResponse save(ComidaRequest request) {
        Comida comida = Comida
                .builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();

        return toResponse(comidaRepository.save(comida));
    }

    /**
     * Este metodo se utilizara para actualizar un registro de comida mediante un id
     *
     * @param id      identificador con el que buscara la comida registrada a actualizar
     * @param request dto de {@link ComidaResponse} con los datos nuevos
     * @return dto de {@link ComidaResponse} con los datos ya actualizados en la base de datos
     */
    @Transactional
    public ComidaResponse updateById(Long id, ComidaRequest request) {
        Comida comidaBD = comidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe comida registrada con el id " + id));

        comidaBD.setNombre(request.getNombre());
        comidaBD.setDescripcion(request.getDescripcion());
        return toResponse(comidaRepository.save(comidaBD));
    }

    /**
     * Este metodo eliminar el registro de una comida de la bd
     *
     * @param id identificador para eliminar el registro
     * @return true si la operacion es exitosa en case que no devolvera un error
     * {@link EntityNotFoundException}
     */
    @Transactional
    public Boolean deleteById(Long id) {
        if (!comidaRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontro el id " + id + " de la comda a eliminar");
        }
        comidaRepository.deleteById(id);
        return true;
    }


    /**
     * Este metodo servira para {@link Comida} en un objeto {@link ComidaResponse}
     *
     * @param comida la entidad a convertir
     * @return el dto convertido
     */
    private ComidaResponse toResponse(Comida comida) {
        return ComidaResponse.builder()
                .id(comida.getId())
                .nombre(comida.getNombre())
                .descripcion(comida.getDescripcion())
                .build();
    }
}
