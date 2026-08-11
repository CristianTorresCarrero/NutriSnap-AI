package com.nutrisnap.controller;

import com.nutrisnap.dto.ResumenNutricionalDiarioResponse;
import com.nutrisnap.service.ResumenNutricionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador encargado de exponer el resumen
 * nutricional diario del usuario autenticado.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/resumen-nutricional")
@RequiredArgsConstructor
public class ResumenNutricionalController {

    private final ResumenNutricionalService resumenNutricionalService;

    /**
     * Obtiene el resumen nutricional correspondiente
     * al día actual del usuario autenticado.
     */
    @GetMapping("/hoy")
    public ResumenNutricionalDiarioResponse obtenerResumenHoy(
            Authentication authentication) {

        return resumenNutricionalService.obtenerResumenDiario(
                authentication.getName()
        );
    }
}