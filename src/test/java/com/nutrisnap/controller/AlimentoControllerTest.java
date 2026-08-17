package com.nutrisnap.controller;

import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.security.CustomUserDetailsService;
import com.nutrisnap.security.JwtService;
import com.nutrisnap.service.AlimentoService;
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

@WebMvcTest(AlimentoController.class)
class AlimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlimentoService alimentoService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void buscarPorId_debeRetornar404_cuandoAlimentoNoExiste()
            throws Exception {

        // Arrange
        Long alimentoId = 9999L;

        when(alimentoService.buscarPorId(alimentoId))
                .thenThrow(
                        new ResourceNotFoundException(
                                "Alimento"
                        )
                );

        // Act + Assert
        mockMvc.perform(
                        get("/api/alimentos/{id}", alimentoId)
                                .with(user("test@nutrisnap.com"))
                )
                .andExpect(
                        status().isNotFound()
                )
                .andExpect(
                        jsonPath("$.success")
                                .value(false)
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                )
                .andExpect(
                        jsonPath("$.message")
                                .value("Alimento no encontrado.")
                );
    }
}