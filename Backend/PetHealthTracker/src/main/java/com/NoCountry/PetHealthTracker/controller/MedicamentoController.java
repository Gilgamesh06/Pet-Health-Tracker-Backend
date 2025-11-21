package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.*;
import com.NoCountry.PetHealthTracker.service.MedicamentoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicament")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService){
        this.medicamentoService = medicamentoService;
    }

    /**
     * Metodo par aguardar un Medicamento
     * @param register DTO -> contiene la informacion a guardar
     * @return MedicamentoInfoDTO and StatusCode 201 CREATED
     */
    @PutMapping("/save")
    public ResponseEntity<MedicamentoInfoDTO> save(@RequestBody RegisterMedicamentoDTO register){
        MedicamentoInfoDTO medicamento = medicamentoService.saveMedicamento(register);
        return new ResponseEntity<>(medicamento, HttpStatus.CREATED);
    }

    /**
     * Metodo para actualizar un Medicamento
     * @param id identificador del medicamento
     * @param update DTO -> contiene los atributos a actualizar
     * @return MedicamentoInfoDTO and StatusCode 20O OK
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<MedicamentoInfoDTO> update(@PathVariable Long id, @RequestBody UpdateMedicamentoDTO update){
        MedicamentoInfoDTO medicamento = medicamentoService.updateMedicamento(id, update);
        return new ResponseEntity<>(medicamento, HttpStatus.OK);
    }

    /**
     * Metodo para eliminar un medicamento
     * @param id identificador del medicamento
     * @return MessageDTO and StatusCode 200 OK
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDTO> delete(@PathVariable Long id){
        MessageDTO message = medicamentoService.deleteMedicamento(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Metodo que retorna los medicamentos activos
     * @param page Pagina a responder
     * @param size  Numero de elementos
     * @param orderBy Orden ascendente o desendente ordenado por nombre
     * @return Page<MedicamentoInfoDTO> and StatusCode 200 OK
     */
    @GetMapping("/")
    public ResponseEntity<Page<MedicamentoInfoDTO>> getMedicamento(@RequestParam Integer page,
                                                      @RequestParam Integer size,
                                                      @RequestParam(defaultValue = "true") Boolean orderBy){
        Page<MedicamentoInfoDTO> medicametos = medicamentoService.getAllMedicamentos(page,size,orderBy);
        return new ResponseEntity<>(medicametos, HttpStatus.OK);
    }

    /**
     * Metodo que retorna los medicamentos por tipo activos
     * @param tipo atributo que define si es vacuna o desparasitante
     * @param page Pagina a responder
     * @param size Numero de elementos
     * @param orderBy Orden ascendente o desendente ordenado por nombre
     * @return Page<MedicamentoInfoDTO> and StatusCode 200 OK
     */
    @GetMapping("/{tipo}")
    public ResponseEntity<Page<MedicamentoInfoDTO>> getMedicamento(@PathVariable String tipo,
                                                                   @RequestParam Integer page,
                                                                   @RequestParam Integer size,
                                                                   @RequestParam(defaultValue = "true") Boolean orderBy){
        Page<MedicamentoInfoDTO> medicametos = medicamentoService.getAllMedicamentosByTipo(page,size,orderBy, tipo);
        return new ResponseEntity<>(medicametos, HttpStatus.OK);
    }


}
