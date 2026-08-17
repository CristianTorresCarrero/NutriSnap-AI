package com.nutrisnap.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrisnap.dto.RegistroPesoRequest;
import com.nutrisnap.service.RegistroPesoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.nutrisnap.security.JwtService;
import com.nutrisnap.security.CustomUserDetailsService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistroPesoController.class)
class RegistroPesoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegistroPesoService registroPesoService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void registrarPeso_debeRetornar400_cuandoPesoEsNegativo()
            throws Exception {

        RegistroPesoRequest request =
                new RegistroPesoRequest();

        request.setPeso(-10.0);

        mockMvc.perform(
                        post("/api/progreso/peso")
                                .with(user("test@nutrisnap.com"))
                                .with(csrf())
                                .contentType("application/json")
                                .content(
                                        objectMapper.writeValueAsString(request)
                                )
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        jsonPath("$.success")
                                .value(false)
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(400)
                )
                .andExpect(
                        jsonPath("$.message")
                                .value(
                                        "El peso debe ser mayor que cero."
                                )
                );
    }
}