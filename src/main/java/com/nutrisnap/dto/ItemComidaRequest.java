package com.nutrisnap.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Representa un alimento y la cantidad consumida
 * dentro de una comida.
 * -------------------------------------------------------
 */
@Data
public class ItemComidaRequest {

    @NotNull(message = "El alimento es obligatorio.")
    private Long alimentoId;

    @NotNull(message = "La cantidad en gramos es obligatoria.")
    @Positive(message = "La cantidad debe ser mayor que cero.")
    private Double gramos;
}