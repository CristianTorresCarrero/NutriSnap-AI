package com.nutrisnap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para actualizar la información
 * básica de un usuario.
 * -------------------------------------------------------
 */
@Data
public class UsuarioUpdateRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @Email(message = "Correo electrónico inválido.")
    @NotBlank(message = "El correo es obligatorio.")
    private String email;
}