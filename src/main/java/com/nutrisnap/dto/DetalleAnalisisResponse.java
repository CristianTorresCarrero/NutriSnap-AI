package com.nutrisnap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleAnalisisResponse {

    private Long alimentoId;
    private String nombreAlimento;
    private Double cantidadGramos;

    private Double calorias;
    private Double proteinas;
    private Double carbohidratos;
    private Double grasas;
    private Double fibra;
    private Double azucares;
    private Double sodio;
}