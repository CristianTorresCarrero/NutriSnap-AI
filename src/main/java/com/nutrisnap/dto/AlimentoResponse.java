package com.nutrisnap.dto;

import com.nutrisnap.enums.CategoriaAlimento;
import lombok.*;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para devolver información de alimentos.
 * -------------------------------------------------------
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimentoResponse {

    private Long id;

    private String nombre;

    private String descripcion;

    private CategoriaAlimento categoria;

    private Double caloriasPor100g;

    private Double proteinasPor100g;

    private Double carbohidratosPor100g;

    private Double grasasPor100g;

    private Double fibraPor100g;

    private Double azucaresPor100g;

    private Double sodioPor100g;

    private Boolean activo;

    private LocalDateTime fechaRegistro;
}