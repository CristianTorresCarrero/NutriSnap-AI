package com.nutrisnap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Entidad encargada de representar a los usuarios
 * registrados en la plataforma.
 *
 * Esta tabla almacena la información necesaria para
 * autenticación y perfil nutricional.
 * -------------------------------------------------------
 */

@Entity
@Table(name = "usuarios")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {

    /* Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private Boolean estado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // ===========================
// Perfil nutricional
// ===========================

    @Column
    private Double peso;

    @Column
    private Double altura;

    @Column
    private Integer edad;

    @Column(length = 20)
    private String sexo;

    @Column(length = 50)
    private String objetivo;

    @Column(length = 30)
    private String nivelActividad;

    @Column
    private LocalDate fechaNacimiento;
}