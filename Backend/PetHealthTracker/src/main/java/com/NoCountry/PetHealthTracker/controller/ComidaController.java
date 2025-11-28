package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.ComidaRequest;
import com.NoCountry.PetHealthTracker.model.dto.ComidaResponse;
import com.NoCountry.PetHealthTracker.service.ComidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comida")
@RequiredArgsConstructor
public class ComidaController {

    private final ComidaService comidaService;

    /**
     * Endpoint que devuelve los datos de una comida de acurdo al id
     *
     * @param id indentificador para buscar la comida en la bd
     * @return ResponseEntity del dto {@link ComidaResponse} con los datos de la comida
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ComidaResponse> getComidaById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(comidaService.getById(id));
    }


    /**
     * Endpoint que devolvera todas las comidas registradas en la bd
     *
     * @return lista de comidas del objeto dto de {@link ComidaResponse}
     */
    @GetMapping(path = "/all")
    public ResponseEntity<List<ComidaResponse>> getAllComida() {
        return ResponseEntity.status(HttpStatus.OK).body(comidaService.getAll());
    }

    /**
     * Este endopoint servira para guardar un nuevo registro de comida en la bd
     *
     * @param request son los datos a guardar en la bd
     * @return ResponseEntity con un dto de {@link ComidaResponse}
     */
    @PostMapping(path = "(/add")
    public ResponseEntity<ComidaResponse> saveComida(@Valid @RequestBody ComidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comidaService.save(request));
    }

    /**
     * Endpoint para actualizar una comida
     *
     * @param id      identificador para encontrar el registro de la comida a actualizar
     * @param request trae los datos actulizados para guardar en la bd
     * @return ResponseEntity con un objeto dto {@link ComidaResponse}
     */
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ComidaResponse> updateyComidaById(@PathVariable(value = "id") Long id, @Valid @RequestBody ComidaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(comidaService.updateById(id, request));
    }

    /**
     * Endpoint para eliminar un registro de comida
     *
     * @param id identificador para buscar el registro a eliminar
     * @return ResponseEntity con estado 200 si se elimina el resgistro correctamente
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
        Boolean isDelete = comidaService.deleteById(id);
        return ResponseEntity.ok(isDelete);
    }
}
