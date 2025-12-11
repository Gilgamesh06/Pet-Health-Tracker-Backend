package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.HorarioRequest;
import com.NoCountry.PetHealthTracker.model.dto.HorarioResponse;
import com.NoCountry.PetHealthTracker.service.HorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/horario")
@RestController
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;


    /**
     * Este endpoint devolverar al usuario una lista de los registro registrados
     *
     * @return lista de horarios mapeados a {@link HorarioResponse}
     */
    @GetMapping(path = "/all")
    public ResponseEntity<List<HorarioResponse>> getHorariosAll() {
        return ResponseEntity.status(HttpStatus.OK).body(horarioService.getAll());
    }


    /**
     * Este enpoint devolvera los datos de un registro horario de la bd
     *
     * @param id identificardor para buscar el registro en la bd
     * @return {@link ResponseEntity} con estado 200 y un cuerpo con el objeto {@link  HorarioResponse}
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<HorarioResponse> getHorarioById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(horarioService.findById(id));
    }

    /**
     * Este enpointe guardara un registro de horario y retornara dicho registro cuando ya este guardado
     *
     * @param request trae los datos a guardar en la base datos
     * @return un {@link ResponseEntity} con un estado de creado y los datos registrados
     */
    @PostMapping(path = "/add")
    public ResponseEntity<HorarioResponse> saveHorario(@Valid @RequestBody HorarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.save(request));
    }


    /**
     * Este endpoint actualizara un registro del bd
     *
     * @param id      identificador para buscar el registro actualizar de la bd
     * @param request trae los datos nuevos
     * @return {@link ResponseEntity} de {@link HorarioResponse} con los datos ya actualizados en la bd
     */
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<HorarioResponse> updateHorarioById(@PathVariable(value = "id") Long id, @RequestBody HorarioRequest request) {
        HorarioResponse horario = horarioService.updateById(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(horario);
    }

    /**
     * Este endpoint desactivara un registro de la base de datos (eliminado logico)
     *
     * @param id identificador para ubicar el registro a desactivar
     * @return ResponseEntity con estado 200 si se elimina el resgistro correctamente
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Boolean> deleteHorarioById(@PathVariable(value = "id") Long id) {
        Boolean isDelete = horarioService.deleteById(id);
        return ResponseEntity.ok(isDelete);
    }


}
