package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.RecordatorioPaginado;
import com.NoCountry.PetHealthTracker.service.RecordatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/recordatorio")
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class RecordatorioController {
    private final RecordatorioService recordatorioService;

    @GetMapping("/{usuario}/{fecha}/{numeroPagina}/{tamanio}")
    public ResponseEntity<RecordatorioPaginado> obtenerRecordatorios(@PathVariable Long usuario, @PathVariable LocalDate fecha,
                                                                     @PathVariable int numeroPagina,@PathVariable int tamanio){
        return new ResponseEntity<>(recordatorioService.obtenerRecordatorios(usuario,fecha,numeroPagina,tamanio), HttpStatus.OK);
    }
}
