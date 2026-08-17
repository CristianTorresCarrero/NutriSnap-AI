package com.nutrisnap.controller;

import com.nutrisnap.dto.ProgresoPesoResponse;
import com.nutrisnap.dto.RegistroPesoRequest;
import com.nutrisnap.dto.RegistroPesoResponse;
import com.nutrisnap.service.RegistroPesoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -------------------------------------------------------
 * Controlador encargado de gestionar el seguimiento
 * de peso del usuario autenticado.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/progreso/peso")
@RequiredArgsConstructor
public class RegistroPesoController {

    private final RegistroPesoService registroPesoService;

    /**
     * Registra una nueva medición de peso.
     */
    @PostMapping
    public RegistroPesoResponse registrarPeso(
            Authentication authentication,
            @Valid @RequestBody RegistroPesoRequest request) {

        return registroPesoService.registrarPeso(
                authentication.getName(),
                request
        );
    }

    /**
     * Obtiene el historial completo de peso.
     */
    @GetMapping
    public List<RegistroPesoResponse> obtenerHistorial(
            Authentication authentication) {

        return registroPesoService.obtenerHistorial(
                authentication.getName()
        );
    }

    /**
     * Obtiene un resumen del progreso de peso.
     */
    @GetMapping("/resumen")
    public ProgresoPesoResponse obtenerProgreso(
            Authentication authentication) {

        return registroPesoService.obtenerProgreso(
                authentication.getName()
        );
    }
}