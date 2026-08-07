package com.nutrisnap.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para calcular una comida formada
 * por uno o varios alimentos.
 * -------------------------------------------------------
 */
@Data
public class CalculoComidaRequest {

    @NotEmpty(message = "La comida debe contener al menos un alimento.")
    @Valid
    private List<ItemComidaRequest> alimentos;
}