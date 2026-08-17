package com.nutrisnap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgresoPesoResponse {

    private Double pesoActual;

    private Double pesoAnterior;

    private Double cambioPeso;

    private Integer totalRegistros;
}