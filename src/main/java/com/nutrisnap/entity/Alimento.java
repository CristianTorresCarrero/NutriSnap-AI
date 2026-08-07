package com.nutrisnap.entity;

import com.nutrisnap.enums.CategoriaAlimento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Entidad encargada de representar los alimentos
 * disponibles en la base nutricional.
 *
 * Los valores nutricionales se almacenan por cada 100 g
 * para poder calcular cualquier tamaño de porción.
 * -------------------------------------------------------
 */
@Entity
@Table(name = "alimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaAlimento categoria;

    @Column(nullable = false)
    private Double caloriasPor100g;

    @Column(nullable = false)
    private Double proteinasPor100g;

    @Column(nullable = false)
    private Double carbohidratosPor100g;

    @Column(nullable = false)
    private Double grasasPor100g;

    private Double fibraPor100g;

    private Double azucaresPor100g;

    private Double sodioPor100g;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}