package com.nutrisnap.service.impl;

import com.nutrisnap.dto.AlimentoResponse;
import com.nutrisnap.entity.Alimento;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.AlimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlimentoServiceImplTest {

    @Mock
    private AlimentoRepository alimentoRepository;

    private AlimentoServiceImpl alimentoService;

    @BeforeEach
    void setUp() {

        alimentoService = new AlimentoServiceImpl(
                alimentoRepository
        );
    }

    @Test
    void buscarPorId_debeLanzarResourceNotFoundException_cuandoAlimentoNoExiste() {

        // Arrange
        Long alimentoId = 9999L;

        when(alimentoRepository.findById(alimentoId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> alimentoService.buscarPorId(alimentoId)
        );
    }

    @Test
    void buscarPorId_debeRetornarAlimento_cuandoExiste() {

        // Arrange
        Long alimentoId = 2L;

        Alimento alimento = Alimento.builder()
                .id(alimentoId)
                .nombre("Arroz integral cocido")
                .caloriasPor100g(123.0)
                .proteinasPor100g(2.7)
                .carbohidratosPor100g(25.6)
                .grasasPor100g(1.0)
                .activo(true)
                .build();

        when(alimentoRepository.findById(alimentoId))
                .thenReturn(Optional.of(alimento));

        // Act
        AlimentoResponse resultado =
                alimentoService.buscarPorId(alimentoId);

        // Assert
        assertNotNull(resultado);

        assertEquals(
                alimentoId,
                resultado.getId()
        );

        assertEquals(
                "Arroz integral cocido",
                resultado.getNombre()
        );

        assertEquals(
                123.0,
                resultado.getCaloriasPor100g()
        );

        assertEquals(
                2.7,
                resultado.getProteinasPor100g()
        );

        assertEquals(
                true,
                resultado.getActivo()
        );
    }
}