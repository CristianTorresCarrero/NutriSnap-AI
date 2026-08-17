package com.nutrisnap.service.impl;

import com.nutrisnap.dto.DashboardNutricionalResponse;
import com.nutrisnap.dto.HistorialDiarioResponse;
import com.nutrisnap.dto.ProgresoPesoResponse;
import com.nutrisnap.dto.ResumenNutricionalDiarioResponse;
import com.nutrisnap.service.AnalisisComidaService;
import com.nutrisnap.service.RegistroPesoService;
import com.nutrisnap.service.ResumenNutricionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardNutricionalServiceImplTest {

    @Mock
    private ResumenNutricionalService resumenNutricionalService;

    @Mock
    private AnalisisComidaService analisisComidaService;

    @Mock
    private RegistroPesoService registroPesoService;

    private DashboardNutricionalServiceImpl dashboardService;

    @BeforeEach
    void setUp() {

        dashboardService = new DashboardNutricionalServiceImpl(
                resumenNutricionalService,
                analisisComidaService,
                registroPesoService
        );
    }

    @Test
    void obtenerDashboard_debeCombinarCorrectamenteLosDatosDelUsuario() {

        // Arrange
        String email = "test@nutrisnap.com";
        LocalDate hoy = LocalDate.now();

        ResumenNutricionalDiarioResponse resumen =
                ResumenNutricionalDiarioResponse.builder()
                        .fecha(hoy)
                        .comidasRegistradas(1)

                        .caloriasObjetivo(2244.69)
                        .caloriasConsumidas(510.50)
                        .caloriasRestantes(1734.19)

                        .proteinasObjetivo(129.60)
                        .proteinasConsumidas(42.85)

                        .carbohidratosObjetivo(291.28)
                        .carbohidratosConsumidos(45.20)

                        .grasasObjetivo(62.35)
                        .grasasConsumidas(17.58)

                        .build();

        HistorialDiarioResponse historial =
                HistorialDiarioResponse.builder()
                        .fecha(hoy)
                        .caloriasDesayuno(0.0)
                        .caloriasAlmuerzo(510.50)
                        .caloriasCena(0.0)
                        .caloriasSnack(0.0)
                        .build();

        ProgresoPesoResponse progresoPeso =
                ProgresoPesoResponse.builder()
                        .pesoActual(71.4)
                        .pesoAnterior(72.0)
                        .cambioPeso(-0.6)
                        .totalRegistros(2)
                        .build();

        when(resumenNutricionalService
                .obtenerResumenDiario(email))
                .thenReturn(resumen);

        when(analisisComidaService
                .obtenerHistorialDiario(
                        eq(email),
                        eq(hoy)
                ))
                .thenReturn(historial);

        when(registroPesoService
                .obtenerProgreso(email))
                .thenReturn(progresoPeso);

        // Act
        DashboardNutricionalResponse resultado =
                dashboardService.obtenerDashboard(email);

        // Assert
        assertNotNull(resultado);

        assertEquals(
                2244.69,
                resultado.getCaloriasObjetivo()
        );

        assertEquals(
                510.50,
                resultado.getCaloriasConsumidas()
        );

        assertEquals(
                1734.19,
                resultado.getCaloriasRestantes()
        );

        assertEquals(
                22.74,
                resultado.getPorcentajeCalorias()
        );

        assertEquals(
                510.50,
                resultado.getCaloriasAlmuerzo()
        );

        assertEquals(
                42.85,
                resultado.getProteinasConsumidas()
        );

        assertEquals(
                71.4,
                resultado.getPesoActual()
        );

        assertEquals(
                72.0,
                resultado.getPesoAnterior()
        );

        assertEquals(
                -0.6,
                resultado.getCambioPeso()
        );

        assertEquals(
                2,
                resultado.getTotalRegistrosPeso()
        );

        assertEquals(
                1,
                resultado.getComidasRegistradas()
        );
    }

    @Test
    void obtenerDashboard_debeFuncionarSinComidasNiRegistrosDePeso() {

        // Arrange
        String email = "nuevo@nutrisnap.com";
        LocalDate hoy = LocalDate.now();

        ResumenNutricionalDiarioResponse resumen =
                ResumenNutricionalDiarioResponse.builder()
                        .fecha(hoy)
                        .comidasRegistradas(0)

                        .caloriasObjetivo(2244.69)
                        .caloriasConsumidas(0.0)
                        .caloriasRestantes(2244.69)

                        .proteinasObjetivo(129.60)
                        .proteinasConsumidas(0.0)

                        .carbohidratosObjetivo(291.28)
                        .carbohidratosConsumidos(0.0)

                        .grasasObjetivo(62.35)
                        .grasasConsumidas(0.0)

                        .build();

        HistorialDiarioResponse historial =
                HistorialDiarioResponse.builder()
                        .fecha(hoy)
                        .caloriasDesayuno(0.0)
                        .caloriasAlmuerzo(0.0)
                        .caloriasCena(0.0)
                        .caloriasSnack(0.0)
                        .build();

        ProgresoPesoResponse progresoPeso =
                ProgresoPesoResponse.builder()
                        .pesoActual(null)
                        .pesoAnterior(null)
                        .cambioPeso(null)
                        .totalRegistros(0)
                        .build();

        when(resumenNutricionalService
                .obtenerResumenDiario(email))
                .thenReturn(resumen);

        when(analisisComidaService
                .obtenerHistorialDiario(
                        eq(email),
                        eq(hoy)
                ))
                .thenReturn(historial);

        when(registroPesoService
                .obtenerProgreso(email))
                .thenReturn(progresoPeso);

        // Act
        DashboardNutricionalResponse resultado =
                dashboardService.obtenerDashboard(email);

        // Assert
        assertNotNull(resultado);

        assertEquals(
                0.0,
                resultado.getCaloriasConsumidas()
        );

        assertEquals(
                2244.69,
                resultado.getCaloriasRestantes()
        );

        assertEquals(
                0.0,
                resultado.getPorcentajeCalorias()
        );

        assertEquals(
                0.0,
                resultado.getCaloriasDesayuno()
        );

        assertEquals(
                0.0,
                resultado.getCaloriasAlmuerzo()
        );

        assertEquals(
                0,
                resultado.getComidasRegistradas()
        );

        assertNull(
                resultado.getPesoActual()
        );

        assertNull(
                resultado.getPesoAnterior()
        );

        assertNull(
                resultado.getCambioPeso()
        );

        assertEquals(
                0,
                resultado.getTotalRegistrosPeso()
        );
    }
}