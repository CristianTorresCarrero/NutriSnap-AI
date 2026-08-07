package com.nutrisnap.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * DTO utilizado para devolver la información
 * del perfil del usuario autenticado.
 * -------------------------------------------------------
 */
@Data
@Builder
public class PerfilResponse {

    private Long id;

    private String nombre;

    private String email;

    private Double peso;

    private Double altura;

    private Integer edad;

    private String sexo;

    private String objetivo;

    private String nivelActividad;

    private LocalDate fechaNacimiento;

}