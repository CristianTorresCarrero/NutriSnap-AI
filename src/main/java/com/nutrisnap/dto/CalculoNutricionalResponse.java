package com.nutrisnap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculoNutricionalResponse {

    private Double imc;

    private String clasificacionIMC;

    private Double tmb;

    private Double caloriasMantenimiento;

    private Double caloriasObjetivo;

    private Double proteinasDiarias;

    private Double grasasDiarias;

    private Double carbohidratosDiarios;

    private Double aguaDiaria;

    private Double porcentajeAjuste;
}