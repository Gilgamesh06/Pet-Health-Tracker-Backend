package com.NoCountry.PetHealthTracker.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity(name = "horario")
public class Horario  extends AuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Time hora;

    @Column(nullable = false)
    private Integer cantidad; // cuanta comida en gramos

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(nullable = false)
    private Boolean recordatorio;

    @ManyToOne
    @JoinColumn(name = "mascota_id", referencedColumnName = "id", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "comida_id", referencedColumnName = "id", nullable = false)
    private Comida comida;
}
