package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
}
