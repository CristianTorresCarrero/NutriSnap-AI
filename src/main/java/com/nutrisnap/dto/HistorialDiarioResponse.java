package com.nutrisnap.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialDiarioResponse {

    private LocalDate fecha;

    private List<AnalisisComidaResponse> desayunos;

    private List<AnalisisComidaResponse> almuerzos;

    private List<AnalisisComidaResponse> cenas;

    private List<AnalisisComidaResponse> snacks;

    private Double caloriasTotales;

    private Double proteinasTotales;

    private Double carbohidratosTotales;

    private Double grasasTotales;

    private Double caloriasDesayuno;

    private Double caloriasAlmuerzo;

    private Double caloriasCena;

    private Double caloriasSnack;
}