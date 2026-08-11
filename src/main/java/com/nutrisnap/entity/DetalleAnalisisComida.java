package com.nutrisnap.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Representa cada alimento incluido dentro
 * de una comida analizada.
 * -------------------------------------------------------
 */
@Entity
@Table(name = "detalle_analisis_comida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleAnalisisComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "analisis_comida_id", nullable = false)
    private AnalisisComida analisisComida;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alimento_id", nullable = false)
    private Alimento alimento;

    @Column(nullable = false)
    private Double cantidadGramos;

    @Column(nullable = false)
    private Double calorias;

    @Column(nullable = false)
    private Double proteinas;

    @Column(nullable = false)
    private Double carbohidratos;

    @Column(nullable = false)
    private Double grasas;

    private Double fibra;

    private Double azucares;

    private Double sodio;
}