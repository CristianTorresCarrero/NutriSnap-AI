package com.nutrisnap.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumenNutricionalDiarioResponse {

    private LocalDate fecha;

    private Integer comidasRegistradas;

    // =========================
    // Calorías
    // =========================

    private Double caloriasObjetivo;
    private Double caloriasConsumidas;
    private Double caloriasRestantes;

    // =========================
    // Proteínas
    // =========================

    private Double proteinasObjetivo;
    private Double proteinasConsumidas;
    private Double proteinasRestantes;

    // =========================
    // Carbohidratos
    // =========================

    private Double carbohidratosObjetivo;
    private Double carbohidratosConsumidos;
    private Double carbohidratosRestantes;

    // =========================
    // Grasas
    // =========================

    private Double grasasObjetivo;
    private Double grasasConsumidas;
    private Double grasasRestantes;

    // =========================
    // Otros nutrientes
    // =========================

    private Double fibraConsumida;
    private Double azucaresConsumidos;
    private Double sodioConsumido;

}
