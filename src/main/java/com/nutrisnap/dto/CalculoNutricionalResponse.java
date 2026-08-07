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

    private Double caloriasDiarias;

    private Double aguaDiaria;
}