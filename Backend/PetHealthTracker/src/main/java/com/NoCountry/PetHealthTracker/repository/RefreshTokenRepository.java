package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.RefreshToken;
import com.NoCountry.PetHealthTracker.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUsuarioAndRevocado(Usuario usuario, Boolean condition);
}
