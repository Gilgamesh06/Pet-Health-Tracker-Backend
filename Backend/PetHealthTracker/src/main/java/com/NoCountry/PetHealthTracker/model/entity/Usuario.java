package com.NoCountry.PetHealthTracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( callSuper = false, onlyExplicitlyIncluded = true)
@Entity(name = "usuario")
public class Usuario extends AuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String email;

    private String password;

    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id", unique = true, nullable = false)
    private Persona persona;
}
