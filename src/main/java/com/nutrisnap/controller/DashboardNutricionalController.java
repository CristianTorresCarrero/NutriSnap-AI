package com.nutrisnap.controller;

import com.nutrisnap.dto.DashboardNutricionalResponse;
import com.nutrisnap.service.DashboardNutricionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador encargado de exponer la información
 * principal del dashboard nutricional del usuario.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardNutricionalController {

    private final DashboardNutricionalService dashboardNutricionalService;

    /**
     * Obtiene el dashboard nutricional correspondiente
     * al día actual del usuario autenticado.
     */
    @GetMapping("/hoy")
    public DashboardNutricionalResponse obtenerDashboardHoy(
            Authentication authentication) {

        return dashboardNutricionalService.obtenerDashboard(
                authentication.getName()
        );
    }
}