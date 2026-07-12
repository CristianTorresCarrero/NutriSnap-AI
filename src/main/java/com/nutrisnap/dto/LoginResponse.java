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

@Data
@Builder
public class LoginResponse {

    private Long id;
    private String nombre;
    private String email;
    private String mensaje;
}