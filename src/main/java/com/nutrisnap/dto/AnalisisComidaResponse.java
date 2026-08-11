package com.nutrisnap.dto;

import com.nutrisnap.entity.TipoComida;
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
    private TipoComida tipoComida;

    private List<DetalleAnalisisResponse> alimentos;
}