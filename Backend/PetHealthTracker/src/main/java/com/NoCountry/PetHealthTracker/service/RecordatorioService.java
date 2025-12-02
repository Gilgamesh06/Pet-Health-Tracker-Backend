package com.NoCountry.PetHealthTracker.service;

import com.NoCountry.PetHealthTracker.model.dto.RecordatorioRequest;
import com.NoCountry.PetHealthTracker.model.dto.RecordatorioResponse;
import com.NoCountry.PetHealthTracker.model.entity.Recordatorio;
import com.NoCountry.PetHealthTracker.repository.ComidaRepository;
import com.NoCountry.PetHealthTracker.repository.RecordatorioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final ComidaService comidaService;
    private final UsuarioService usuarioService;

    /**
     * Este metodo registrara un recordatorio en la base de datos
     * @param request dto {@link RecordatorioRequest} que trae los datos a guardar
     * @return un dto {@link  RecordatorioResponse} con los datos guardados en la base de datos
     */
    @Transactional
    public RecordatorioResponse save(RecordatorioRequest request){
        return null;
    }



    private RecordatorioResponse toResponse(Recordatorio recordatorio){
        return RecordatorioResponse.builder()

                .build();
    }

}
