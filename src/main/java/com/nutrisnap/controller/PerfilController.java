package com.nutrisnap.controller;

import com.nutrisnap.dto.PerfilResponse;
import com.nutrisnap.dto.PerfilUpdateRequest;
import com.nutrisnap.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador encargado de gestionar el perfil
 * del usuario autenticado.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    /**
     * Obtiene el perfil del usuario autenticado.
     */
    @GetMapping("/me")
    public PerfilResponse obtenerPerfil(Authentication authentication) {

        return perfilService.obtenerPerfil(authentication.getName());

    }

    /**
     * Actualiza el perfil del usuario autenticado.
     */
    @PutMapping("/me")
    public PerfilResponse actualizarPerfil(
            Authentication authentication,
            @Valid @RequestBody PerfilUpdateRequest request) {

        return perfilService.actualizarPerfil(
                authentication.getName(),
                request);

    }

}