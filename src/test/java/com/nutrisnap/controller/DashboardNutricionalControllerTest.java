package com.nutrisnap.controller;

import com.nutrisnap.dto.DashboardNutricionalResponse;
import com.nutrisnap.security.CustomUserDetailsService;
import com.nutrisnap.security.JwtService;
import com.nutrisnap.service.DashboardNutricionalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardNutricionalController.class)
class DashboardNutricionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DashboardNutricionalService dashboardNutricionalService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void obtenerDashboardHoy_debeRetornar200_cuandoUsuarioEstaAutenticado()
            throws Exception {

        // Arrange
        String email = "test@nutrisnap.com";

        DashboardNutricionalResponse response =
                DashboardNutricionalResponse.builder()
                        .caloriasObjetivo(2244.69)
                        .caloriasConsumidas(510.50)
                        .caloriasRestantes(1734.19)
                        .porcentajeCalorias(22.74)
                        .comidasRegistradas(1)
                        .caloriasDesayuno(0.0)
                        .caloriasAlmuerzo(510.50)
                        .caloriasCena(0.0)
                        .caloriasSnack(0.0)
                        .pesoActual(71.4)
                        .pesoAnterior(72.0)
                        .cambioPeso(-0.6)
                        .totalRegistrosPeso(2)
                        .build();

        when(dashboardNutricionalService.obtenerDashboard(email))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(
                        get("/api/dashboard/hoy")
                                .with(user(email))
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.caloriasObjetivo")
                                .value(2244.69)
                )
                .andExpect(
                        jsonPath("$.caloriasConsumidas")
                                .value(510.50)
                )
                .andExpect(
                        jsonPath("$.caloriasRestantes")
                                .value(1734.19)
                )
                .andExpect(
                        jsonPath("$.porcentajeCalorias")
                                .value(22.74)
                )
                .andExpect(
                        jsonPath("$.comidasRegistradas")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.caloriasAlmuerzo")
                                .value(510.50)
                )
                .andExpect(
                        jsonPath("$.pesoActual")
                                .value(71.4)
                )
                .andExpect(
                        jsonPath("$.cambioPeso")
                                .value(-0.6)
                );
    }
}