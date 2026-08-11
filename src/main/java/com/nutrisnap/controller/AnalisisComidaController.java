package com.nutrisnap.controller;

import com.nutrisnap.dto.AnalisisComidaResponse;
import com.nutrisnap.dto.CalculoComidaRequest;
import com.nutrisnap.service.AnalisisComidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador encargado de guardar y consultar
 * los análisis de comidas del usuario autenticado.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/analisis-comidas")
@RequiredArgsConstructor
public class AnalisisComidaController {

    private final AnalisisComidaService analisisComidaService;

    /**
     * Calcula una comida y la guarda en el historial
     * del usuario autenticado.
     */
    @PostMapping
    public AnalisisComidaResponse calcularYGuardar(
            Authentication authentication,
            @Valid @RequestBody CalculoComidaRequest request) {

        return analisisComidaService.calcularYGuardar(
                authentication.getName(),
                request
        );
    }

    /**
     * Obtiene el historial de comidas
     * del usuario autenticado.
     */
    @GetMapping
    public List<AnalisisComidaResponse> obtenerHistorial(
            Authentication authentication) {

        return analisisComidaService.obtenerHistorial(
                authentication.getName()
        );
    }

    @GetMapping("/fecha/{fecha}")
    public List<AnalisisComidaResponse> obtenerPorFecha(
            Authentication authentication,
            @PathVariable LocalDate fecha) {

        return analisisComidaService.obtenerHistorialPorFecha(
                authentication.getName(),
                fecha
        );
    }
}