package com.nutrisnap.service.impl;

import com.nutrisnap.dto.DashboardNutricionalResponse;
import com.nutrisnap.dto.HistorialDiarioResponse;
import com.nutrisnap.dto.ProgresoPesoResponse;
import com.nutrisnap.dto.ResumenNutricionalDiarioResponse;
import com.nutrisnap.service.AnalisisComidaService;
import com.nutrisnap.service.DashboardNutricionalService;
import com.nutrisnap.service.RegistroPesoService;
import com.nutrisnap.service.ResumenNutricionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * -------------------------------------------------------
 * Servicio encargado de construir la información
 * principal del dashboard nutricional del usuario.
 *
 * Combina los objetivos diarios con el consumo
 * registrado y las calorías por tipo de comida.
 * -------------------------------------------------------
 */
@Service
@RequiredArgsConstructor
public class DashboardNutricionalServiceImpl implements DashboardNutricionalService {

    private final ResumenNutricionalService resumenNutricionalService;
    private final AnalisisComidaService analisisComidaService;
    private final RegistroPesoService registroPesoService;

    @Override
    public DashboardNutricionalResponse obtenerDashboard(String email) {

        // =========================================
        // Obtener resumen nutricional del día
        // =========================================

        ResumenNutricionalDiarioResponse resumen =
                resumenNutricionalService
                        .obtenerResumenDiario(email);

        // =========================================
        // Obtener historial agrupado de hoy
        // =========================================

        LocalDate hoy = LocalDate.now();

        HistorialDiarioResponse historial =
                analisisComidaService
                        .obtenerHistorialDiario(
                                email,
                                hoy
                        );
        ProgresoPesoResponse progresoPeso =
                registroPesoService.obtenerProgreso(email);

        // =========================================
        // Calcular porcentaje de calorías
        // =========================================

        Double porcentajeCalorias =
                calcularPorcentaje(
                        resumen.getCaloriasConsumidas(),
                        resumen.getCaloriasObjetivo()
                );

        // =========================================
        // Construir respuesta del dashboard
        // =========================================

        return DashboardNutricionalResponse.builder()

                .fecha(hoy)

                .caloriasObjetivo(
                        resumen.getCaloriasObjetivo())

                .caloriasConsumidas(
                        resumen.getCaloriasConsumidas())

                .caloriasRestantes(
                        resumen.getCaloriasRestantes())

                .porcentajeCalorias(
                        porcentajeCalorias)

                .proteinasObjetivo(
                        resumen.getProteinasObjetivo())

                .proteinasConsumidas(
                        resumen.getProteinasConsumidas())

                .carbohidratosObjetivo(
                        resumen.getCarbohidratosObjetivo())

                .carbohidratosConsumidos(
                        resumen.getCarbohidratosConsumidos())

                .grasasObjetivo(
                        resumen.getGrasasObjetivo())

                .grasasConsumidas(
                        resumen.getGrasasConsumidas())

                .caloriasDesayuno(
                        historial.getCaloriasDesayuno())

                .caloriasAlmuerzo(
                        historial.getCaloriasAlmuerzo())

                .caloriasCena(
                        historial.getCaloriasCena())

                .caloriasSnack(
                        historial.getCaloriasSnack())

                .comidasRegistradas(
                        resumen.getComidasRegistradas())

                .pesoActual(
                        progresoPeso.getPesoActual())

                .pesoAnterior(
                        progresoPeso.getPesoAnterior())

                .cambioPeso(
                        progresoPeso.getCambioPeso())

                .totalRegistrosPeso(
                        progresoPeso.getTotalRegistros())

                .build();
    }


    /**
     * Calcula el porcentaje de calorías consumidas
     * frente al objetivo diario.
     */
    private Double calcularPorcentaje(
            Double consumidas,
            Double objetivo) {

        if (consumidas == null ||
                objetivo == null ||
                objetivo <= 0) {

            return null;
        }

        double porcentaje =
                (consumidas / objetivo) * 100;

        return Math.round(
                porcentaje * 100.0
        ) / 100.0;
    }
}