package com.nutrisnap.dto;

import com.nutrisnap.enums.NivelActividad;
import com.nutrisnap.enums.ObjetivoNutricional;
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

    private ObjetivoNutricional objetivo;

    private NivelActividad nivelActividad;

    private LocalDate fechaNacimiento;

}