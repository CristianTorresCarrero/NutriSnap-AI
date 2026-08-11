package com.nutrisnap.dto;

import com.nutrisnap.entity.TipoComida;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

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

    @NotNull(message = "El tipo de comida es obligatorio.")
    private TipoComida tipoComida;

    @NotEmpty(message = "La comida debe contener al menos un alimento.")
    @Valid
    private List<ItemComidaRequest> alimentos;
}