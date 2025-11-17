package com.NoCountry.PetHealthTracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Set<Rol> roles = new HashSet<>();


    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id", unique = true, nullable = false)
    private Persona persona;

    public enum Rol {
        ADMIN,
        USER,
        MODERATOR,
        GUEST
    }

    @PrePersist
    public void prePersist() {
        if (roles.isEmpty()) {
            roles.add(Rol.USER);
        }
    }
}
