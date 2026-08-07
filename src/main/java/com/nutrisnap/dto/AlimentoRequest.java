package com.nutrisnap.dto;

import com.nutrisnap.enums.CategoriaAlimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para registrar o actualizar alimentos.
 * -------------------------------------------------------
 */
@Data
public class AlimentoRequest {

    @NotBlank(message = "El nombre del alimento es obligatorio.")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La categoría es obligatoria.")
    private CategoriaAlimento categoria;

    @NotNull(message = "Las calorías son obligatorias.")
    @PositiveOrZero(message = "Las calorías no pueden ser negativas.")
    private Double caloriasPor100g;

    @NotNull(message = "Las proteínas son obligatorias.")
    @PositiveOrZero(message = "Las proteínas no pueden ser negativas.")
    private Double proteinasPor100g;

    @NotNull(message = "Los carbohidratos son obligatorios.")
    @PositiveOrZero(message = "Los carbohidratos no pueden ser negativos.")
    private Double carbohidratosPor100g;

    @NotNull(message = "Las grasas son obligatorias.")
    @PositiveOrZero(message = "Las grasas no pueden ser negativas.")
    private Double grasasPor100g;

    @PositiveOrZero(message = "La fibra no puede ser negativa.")
    private Double fibraPor100g;

    @PositiveOrZero(message = "Los azúcares no pueden ser negativos.")
    private Double azucaresPor100g;

    @PositiveOrZero(message = "El sodio no puede ser negativo.")
    private Double sodioPor100g;
}