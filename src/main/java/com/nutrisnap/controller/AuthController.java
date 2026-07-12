package com.nutrisnap.controller;

import com.nutrisnap.dto.LoginRequest;
import com.nutrisnap.dto.LoginResponse;
import com.nutrisnap.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador encargado de la autenticación.
 * -------------------------------------------------------
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Inicio de sesión.
     */
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return usuarioService.login(request);
    }
}