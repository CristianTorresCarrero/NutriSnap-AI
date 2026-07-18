package com.nutrisnap.dto;

import lombok.Builder;
import lombok.Data;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para responder después
 * de un inicio de sesión exitoso.
 * -------------------------------------------------------
 */

@Builder
@Data
public class LoginResponse {

    private String token;

    private String tokenType;

    private Long expiresIn;

    private String nombre;

    private String email;
}