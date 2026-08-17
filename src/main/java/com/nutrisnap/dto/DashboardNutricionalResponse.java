package com.nutrisnap.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardNutricionalResponse {

    private LocalDate fecha;

    // =========================
    // Calorías generales
    // =========================

    private Double caloriasObjetivo;
    private Double caloriasConsumidas;
    private Double caloriasRestantes;
    private Double porcentajeCalorias;

    // =========================
    // Macronutrientes
    // =========================

    private Double proteinasObjetivo;
    private Double proteinasConsumidas;

    private Double carbohidratosObjetivo;
    private Double carbohidratosConsumidos;

    private Double grasasObjetivo;
    private Double grasasConsumidas;

    // =========================
    // Calorías por tipo comida
    // =========================

    private Double caloriasDesayuno;
    private Double caloriasAlmuerzo;
    private Double caloriasCena;
    private Double caloriasSnack;

    // =========================
    // Información adicional
    // =========================

    private Integer comidasRegistradas;

    private Double pesoActual;
    private Double pesoAnterior;
    private Double cambioPeso;
    private Integer totalRegistrosPeso;
}