package com.nutrisnap.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * -------------------------------------------------------
  DTO utilizado para registrar una nueva medición
  de peso del usuario autenticado.
 * -------------------------------------------------------
 */
@Data
public class RegistroPesoRequest {

    @NotNull(message = "El peso es obligatorio.")
    @Positive(message = "El peso debe ser mayor que cero.")
    private Double peso;
}