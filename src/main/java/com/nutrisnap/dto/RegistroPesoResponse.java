package com.nutrisnap.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------------
 * DTO utilizado para devolver un registro
 * del historial de peso.
 * -------------------------------------------------------
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroPesoResponse {

    private Long id;

    private Double peso;

    private LocalDateTime fechaRegistro;
}