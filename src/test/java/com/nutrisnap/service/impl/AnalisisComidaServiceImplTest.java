package com.nutrisnap.service.impl;

import com.nutrisnap.dto.AnalisisComidaResponse;
import com.nutrisnap.dto.CalculoComidaRequest;
import com.nutrisnap.dto.CalculoComidaResponse;
import com.nutrisnap.dto.ItemComidaRequest;
import com.nutrisnap.dto.PorcionNutricionalResponse;
import com.nutrisnap.entity.Alimento;
import com.nutrisnap.entity.AnalisisComida;
import com.nutrisnap.entity.TipoComida;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.AlimentoRepository;
import com.nutrisnap.repository.AnalisisComidaRepository;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.AlimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalisisComidaServiceImplTest {

    @Mock
    private AnalisisComidaRepository analisisComidaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AlimentoRepository alimentoRepository;

    @Mock
    private AlimentoService alimentoService;

    private AnalisisComidaServiceImpl analisisComidaService;

    @BeforeEach
    void setUp() {

        analisisComidaService = new AnalisisComidaServiceImpl(
                analisisComidaRepository,
                usuarioRepository,
                alimentoRepository,
                alimentoService
        );
    }

    @Test
    void calcularYGuardar_debeGuardarAnalisisCorrectamente() {

        // =========================
        // Arrange
        // =========================

        String email = "test@nutrisnap.com";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .email(email)
                .build();

        Alimento alimento = Alimento.builder()
                .id(2L)
                .nombre("Arroz integral cocido")
                .activo(true)
                .build();

        ItemComidaRequest item = new ItemComidaRequest();
        item.setAlimentoId(2L);
        item.setGramos(150.0);

        CalculoComidaRequest request =
                new CalculoComidaRequest();

        request.setTipoComida(TipoComida.ALMUERZO);
        request.setAlimentos(List.of(item));

        PorcionNutricionalResponse porcion =
                PorcionNutricionalResponse.builder()
                        .alimentoId(2L)
                        .nombre("Arroz integral cocido")
                        .cantidadGramos(150.0)
                        .calorias(184.5)
                        .proteinas(4.05)
                        .carbohidratos(38.4)
                        .grasas(1.5)
                        .fibra(2.4)
                        .azucares(0.3)
                        .sodio(6.0)
                        .build();

        CalculoComidaResponse calculo =
                CalculoComidaResponse.builder()
                        .alimentos(List.of(porcion))
                        .caloriasTotales(184.5)
                        .proteinasTotales(4.05)
                        .carbohidratosTotales(38.4)
                        .grasasTotales(1.5)
                        .fibraTotal(2.4)
                        .azucaresTotales(0.3)
                        .sodioTotal(6.0)
                        .build();

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuario));

        when(alimentoService.calcularComida(request))
                .thenReturn(calculo);

        when(alimentoRepository.findById(2L))
                .thenReturn(Optional.of(alimento));

        when(analisisComidaRepository.save(any(AnalisisComida.class)))
                .thenAnswer(invocation -> {

                    AnalisisComida analisis =
                            invocation.getArgument(0);

                    analisis.setId(1L);

                    return analisis;
                });

        // =========================
        // Act
        // =========================

        AnalisisComidaResponse resultado =
                analisisComidaService.calcularYGuardar(
                        email,
                        request
                );

        // =========================
        // Assert
        // =========================

        assertNotNull(resultado);

        assertEquals(
                1L,
                resultado.getId()
        );

        assertEquals(
                TipoComida.ALMUERZO,
                resultado.getTipoComida()
        );

        assertEquals(
                184.5,
                resultado.getCaloriasTotales()
        );

        assertEquals(
                4.05,
                resultado.getProteinasTotales()
        );

        assertEquals(
                1,
                resultado.getAlimentos().size()
        );

        assertEquals(
                "Arroz integral cocido",
                resultado.getAlimentos()
                        .get(0)
                        .getNombreAlimento()
        );

        assertEquals(
                150.0,
                resultado.getAlimentos()
                        .get(0)
                        .getCantidadGramos()
        );
    }

    @Test
    void calcularYGuardar_debeLanzarResourceNotFoundException_cuandoUsuarioNoExiste() {

        String email = "noexiste@nutrisnap.com";

        CalculoComidaRequest request =
                new CalculoComidaRequest();

        request.setTipoComida(TipoComida.ALMUERZO);
        request.setAlimentos(List.of());

        when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> analisisComidaService.calcularYGuardar(
                        email,
                        request
                )
        );

        verify(
                analisisComidaRepository,
                never()
        ).save(any(AnalisisComida.class));
    }
}