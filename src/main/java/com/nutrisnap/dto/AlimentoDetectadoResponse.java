package com.nutrisnap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlimentoDetectadoResponse {

    private String nombre;

    private Double confianza;
}