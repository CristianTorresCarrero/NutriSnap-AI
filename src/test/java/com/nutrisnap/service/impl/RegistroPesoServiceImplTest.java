package com.nutrisnap.service.impl;

import com.nutrisnap.dto.ProgresoPesoResponse;
import com.nutrisnap.entity.RegistroPeso;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.repository.RegistroPesoRepository;
import com.nutrisnap.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistroPesoServiceImplTest {

    @Mock
    private RegistroPesoRepository registroPesoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private RegistroPesoServiceImpl registroPesoService;

    @BeforeEach
    void setUp() {

        registroPesoService = new RegistroPesoServiceImpl(
                registroPesoRepository,
                usuarioRepository
        );
    }

    @Test
    void obtenerProgreso_debeCalcularCambioEntreDosUltimosPesos() {

        // Arrange
        String email = "test@nutrisnap.com";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email(email)
                .build();

        RegistroPeso pesoActual = RegistroPeso.builder()
                .id(2L)
                .usuario(usuario)
                .peso(71.4)
                .fechaRegistro(LocalDateTime.now())
                .build();

        RegistroPeso pesoAnterior = RegistroPeso.builder()
                .id(1L)
                .usuario(usuario)
                .peso(72.0)
                .fechaRegistro(LocalDateTime.now().minusDays(7))
                .build();

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(registroPesoRepository
                .findByUsuarioIdOrderByFechaRegistroDesc(1L))
                .thenReturn(List.of(
                        pesoActual,
                        pesoAnterior
                ));

        // Act
        ProgresoPesoResponse resultado =
                registroPesoService.obtenerProgreso(email);

        // Assert
        assertNotNull(resultado);

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
                resultado.getTotalRegistros()
        );
    }

    @Test
    void obtenerProgreso_debeRetornarValoresNulos_cuandoSoloExisteUnRegistro() {

        // Arrange
        String email = "test@nutrisnap.com";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email(email)
                .build();

        RegistroPeso unicoRegistro = RegistroPeso.builder()
                .id(1L)
                .usuario(usuario)
                .peso(71.4)
                .fechaRegistro(LocalDateTime.now())
                .build();

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(registroPesoRepository
                .findByUsuarioIdOrderByFechaRegistroDesc(1L))
                .thenReturn(List.of(unicoRegistro));

        // Act
        ProgresoPesoResponse resultado =
                registroPesoService.obtenerProgreso(email);

        // Assert
        assertNotNull(resultado);

        assertEquals(
                71.4,
                resultado.getPesoActual()
        );

        assertNull(
                resultado.getPesoAnterior()
        );

        assertNull(
                resultado.getCambioPeso()
        );

        assertEquals(
                1,
                resultado.getTotalRegistros()
        );
    }

    @Test
    void obtenerProgreso_debeRetornarVacio_cuandoNoHayRegistros() {

        // Arrange
        String email = "test@nutrisnap.com";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email(email)
                .build();

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(registroPesoRepository
                .findByUsuarioIdOrderByFechaRegistroDesc(1L))
                .thenReturn(List.of());

        // Act
        ProgresoPesoResponse resultado =
                registroPesoService.obtenerProgreso(email);

        // Assert
        assertNotNull(resultado);

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
                resultado.getTotalRegistros()
        );
    }
}