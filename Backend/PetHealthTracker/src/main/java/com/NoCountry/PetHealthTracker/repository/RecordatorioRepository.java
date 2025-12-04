package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.Recordatorio;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RecordatorioRepository extends JpaRepository<Recordatorio,Long> {

    Page<Recordatorio>findByFechaProgramadaBetweenAndUsuario(LocalDateTime inicio, LocalDateTime fin, Usuario usuario, Pageable pageable);

}
