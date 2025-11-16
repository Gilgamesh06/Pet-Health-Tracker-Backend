package com.NoCountry.PetHealthTracker.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
public class AuditoriaModel {

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    @Column(name = "estado", nullable = false)
    private String estado;

    @PrePersist
    public void prePersist() {
        if (estado == null) {
            estado = "ACTIVO";
        }
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
