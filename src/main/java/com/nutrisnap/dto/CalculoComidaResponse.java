package com.nutrisnap.dto;

import lombok.*;

import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Resultado nutricional total de una comida.
 * -------------------------------------------------------
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculoComidaResponse {

    private List<PorcionNutricionalResponse> alimentos;

    private Double caloriasTotales;

    private Double proteinasTotales;

    private Double carbohidratosTotales;

    private Double grasasTotales;

    private Double fibraTotal;

    private Double azucaresTotales;

    private Double sodioTotal;
}