package com.NoCountry.PetHealthTracker.repository;

import com.NoCountry.PetHealthTracker.model.entity.Mascota;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@DataJpaTest
public class MascotaRepositoryTest {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Test
    void saveMascota(){
        Mascota mascota = Mascota.builder()
                .nombre("Apolo")
                .especie("Perro")
                .raza("Chihuahua")
                .fechaNacimiento(LocalDate.now())
                .peso(7.8)
                .foto("Foto.png")
                .usuario(null)
                .build();
        Mascota saved = mascotaRepository.save(mascota);

        assertThat(saved.getId()).isNotZero();
        assertThat(saved.getNombre()).isEqualTo("Apolo");
    }

}
