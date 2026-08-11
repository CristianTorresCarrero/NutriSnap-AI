package com.nutrisnap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Representa una comida analizada y almacenada
 * dentro del historial nutricional del usuario.
 * -------------------------------------------------------
 */
@Entity
@Table(name = "analisis_comida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Double caloriasTotales;

    @Column(nullable = false)
    private Double proteinasTotales;

    @Column(nullable = false)
    private Double carbohidratosTotales;

    @Column(nullable = false)
    private Double grasasTotales;

    private Double fibraTotal;

    private Double azucaresTotales;

    private Double sodioTotal;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(
            mappedBy = "analisisComida",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<DetalleAnalisisComida> detalles = new ArrayList<>();
}