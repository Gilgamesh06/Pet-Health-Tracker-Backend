package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.Medicamento;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    Optional<Medicamento> findByIdAndUsuario(Long id, Usuario usuario);

    Page<Medicamento> findAllByUsuarioAndEstado(Usuario usuario,String estado,Pageable pageable);

    Page<Medicamento> findAllByUsuarioAndEstadoAndTipo(Usuario usuario,String estado,String tipo, Pageable pageable);

    Optional<Medicamento> findByNombreAndTipoAndFabricanteAndIntervaloDosisAndUsuario(String nombre, String tipo, String fabricante, Integer intervaloDosis, Usuario usuario);
}
