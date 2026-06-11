package com.nutrisnap.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 -------------------------------------------------------
 Proyecto: NutriSnap AI

 Entidad encargada de representar a los usuarios
 registrados en la plataforma.

 Esta tabla almacena la información necesaria para
 autenticación y gestión de cuentas.
 -------------------------------------------------------
 */

@Entity
@Table(name = "usuarios")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {

    /*Indentificador unico del Usuario*/
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
}
