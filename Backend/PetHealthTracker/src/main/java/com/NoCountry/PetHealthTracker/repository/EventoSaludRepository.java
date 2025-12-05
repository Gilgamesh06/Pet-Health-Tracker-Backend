package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.EventoSalud;
import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface EventoSaludRepository extends JpaRepository<EventoSalud,Long> {
 Page<EventoSalud> findByFechaProgramadaBetweenAndMascota(LocalDateTime inicio, LocalDateTime fin, Mascota mascota, Pageable pageable);

}
