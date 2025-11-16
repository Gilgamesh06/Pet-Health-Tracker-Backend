package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
}
