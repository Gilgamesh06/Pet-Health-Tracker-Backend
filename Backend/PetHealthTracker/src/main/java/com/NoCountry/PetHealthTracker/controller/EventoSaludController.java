package com.NoCountry.PetHealthTracker.controller;

import com.NoCountry.PetHealthTracker.model.dto.EventoSaludPaginacion;
import com.NoCountry.PetHealthTracker.model.dto.RecordatorioPaginado;
import com.NoCountry.PetHealthTracker.service.EventoSaludService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/evento-salud")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
public class EventoSaludController {
    private final EventoSaludService eventoSaludService;

    @GetMapping("/{mascota}/{fecha}/{numeroPagina}/{tamanio}")
    public ResponseEntity<EventoSaludPaginacion> obtenerEventosSaludPorMascotaFecha(@PathVariable Long mascota, @PathVariable LocalDate fecha,
                                                                                             @PathVariable int numeroPagina, @PathVariable int tamanio){
        return new ResponseEntity<>(eventoSaludService.obtenerEventosSaludPorMascota(mascota,fecha,numeroPagina,tamanio), HttpStatus.OK);
    }
}
