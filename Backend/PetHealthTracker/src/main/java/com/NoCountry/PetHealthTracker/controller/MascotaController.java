package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.MascotaRequest;
import com.NoCountry.PetHealthTracker.model.dto.MascotaResponse;
import com.NoCountry.PetHealthTracker.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/mascota")
@RestController
@RequiredArgsConstructor
public class MascotaController {

    /**
     * Servicio encargado de las operaciones relacionadas con la mascota
     */
    private final MascotaService mascotaService;

    /**
     * Endpoint para obtener todas las mascotas registradas por un usuario
     *
     * @param id identificador del usuario
     * @return ResponseEntity con estdo 200 y una lista de {@link MascotaResponse} con todas las mascotas registradas
     */
    @GetMapping(path = "/all/{id}")
    public ResponseEntity<List<MascotaResponse>> getAllMascotas(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mascotaService.getAll(id));
    }

    /**
     * Enpoint para obtener la informacion de una mascota por su id
     *
     * @param id identificador de la mascota a consultar
     * @return ResponseEntity con estado 200 y {@link MascotaResponse} con los datos de la mascota
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<MascotaResponse> getMascotaById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mascotaService.getById(id));
    }

    /**
     * Endpoint para registrar una nueva mascota a un usuario valido
     *
     * @param request dto trae los datos a guardar de la mascota
     * @return ResponseEntity con estdo 201 y {@link MascotaResponse} con los datos de la mascota guardada
     */
    @PostMapping(path = "/add")
    public ResponseEntity<MascotaResponse> saveMascota(@Valid @RequestBody MascotaRequest request) {

        MascotaResponse response = mascotaService.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Endpoint para actualizar la mascota registrada a un usuario
     *
     * @param id      de la mascota a actualizar
     * @param request contiene los datos de la mascota a actualizar
     * @return ResponseEntity con estado 200 y {@link MascotaResponse} con los datos ya actualizas en la base de datos
     */
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<MascotaResponse> updateMascotaById(@PathVariable(value = "id") Long id,
                                                             @Valid @RequestBody MascotaRequest request) {
        MascotaResponse response = mascotaService.updateById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * Endpoint para eliminar una mascota
     *
     * @param id extraido de la url para eliminar una mascota
     * @return ResponseEntity con estado 200 si se elimina el resgistro correctamente
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Boolean> deleteMascotaById(@PathVariable(value = "id") Long id) {
        Boolean isDelete = mascotaService.deleteById(id);
        return ResponseEntity.ok(isDelete);
    }
}
