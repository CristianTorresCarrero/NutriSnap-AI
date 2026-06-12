package com.nutrisnap.dto;


import lombok.Builder;
import lombok.Data;

/**
 * -------------------------------------------------------
 * DTO utilizado para responder al cliente
 * después de registrar un usuario.
 * -------------------------------------------------------
 */

@Data
@Builder
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private String mensaje;
}
