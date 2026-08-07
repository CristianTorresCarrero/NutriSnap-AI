package com.nutrisnap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para actualizar el perfil
 * del usuario autenticado.
 * -------------------------------------------------------
 */
@Data
public class PerfilUpdateRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @Email(message = "Correo electrónico inválido.")
    @NotBlank(message = "El correo es obligatorio.")
    private String email;

    @Positive(message = "El peso debe ser mayor que cero.")
    private Double peso;

    @Positive(message = "La altura debe ser mayor que cero.")
    private Double altura;

    @Positive(message = "La edad debe ser mayor que cero.")
    private Integer edad;

    private String sexo;

    private String objetivo;

    private String nivelActividad;

    private LocalDate fechaNacimiento;

}