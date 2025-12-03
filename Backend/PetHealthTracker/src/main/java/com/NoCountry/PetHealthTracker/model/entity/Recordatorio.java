package com.NoCountry.PetHealthTracker.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity(name = "recordatorio")
public class Recordatorio extends AuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "fecha_recordatorio", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaRecordatorio;

    @Column(nullable = false)
    private String canal;

    @Column(name = "estado_recordatorio", nullable = false)
    private String estadoRecordatorio;

    @Column(nullable = false)
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "evento_salud_id", referencedColumnName = "id")
    private EventoSalud eventoSalud;

    @ManyToOne
    @JoinColumn(name = "horario_id", referencedColumnName = "id")
    private Horario horario;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;
}
