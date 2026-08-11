package com.nutrisnap.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalisisComidaResponse {

    private Long id;
    private LocalDateTime fechaRegistro;

    private Double caloriasTotales;
    private Double proteinasTotales;
    private Double carbohidratosTotales;
    private Double grasasTotales;
    private Double fibraTotal;
    private Double azucaresTotales;
    private Double sodioTotal;

    private List<DetalleAnalisisResponse> alimentos;
}