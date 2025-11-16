package com.NoCountry.PetHealthTracker.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(name = "fecha_expiracion",nullable = false)
    private Date fechaExpiracion;

    @Column(name = "fecha_revocado")
    private Date fechaRevocado;

    @Column(, nullable = false)
    private Boolean revocado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        if (revocado == null) {
            revocado = false;
        }
    }


}
