package com.nutrisnap.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PorcionNutricionalResponse {

    private Long alimentoId;

    private String nombre;

    private Double cantidadGramos;

    private Double calorias;

    private Double proteinas;

    private Double carbohidratos;

    private Double grasas;

    private Double fibra;

    private Double azucares;

    private Double sodio;
}