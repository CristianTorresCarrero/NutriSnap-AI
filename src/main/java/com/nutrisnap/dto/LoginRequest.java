package com.nutrisnap.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para recibir las credenciales
 * de inicio de sesión.
 * -------------------------------------------------------
 */

@Data
public class LoginRequest {
    @Email(message = "Correo Invalido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
