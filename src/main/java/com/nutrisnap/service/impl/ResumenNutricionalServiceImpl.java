package com.nutrisnap.service.impl;

import com.nutrisnap.dto.CalculoNutricionalResponse;
import com.nutrisnap.dto.ResumenNutricionalDiarioResponse;
import com.nutrisnap.entity.AnalisisComida;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.AnalisisComidaRepository;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.NutricionService;
import com.nutrisnap.service.ResumenNutricionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Servicio encargado de generar el resumen nutricional
 * diario del usuario autenticado.
 *
 * Combina los objetivos nutricionales del usuario con
 * las comidas registradas durante el día.
 * -------------------------------------------------------
 */
@Service
@RequiredArgsConstructor
public class ResumenNutricionalServiceImpl
        implements ResumenNutricionalService {

    private final UsuarioRepository usuarioRepository;

    private final AnalisisComidaRepository analisisComidaRepository;

    private final NutricionService nutricionService;


    @Override
    @Transactional(readOnly = true)
    public ResumenNutricionalDiarioResponse obtenerResumenDiario(
            String email) {

        // =========================================
        // 1. Buscar al usuario autenticado
        // =========================================

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));


        // =========================================
        // 2. Obtener objetivos nutricionales
        // =========================================

        CalculoNutricionalResponse objetivos =
                nutricionService.calcularNutricion(email);


        // =========================================
        // 3. Obtener fecha actual
        // =========================================

        LocalDate hoy = LocalDate.now();

        LocalDateTime inicioDia =
                hoy.atStartOfDay();

        LocalDateTime finDia =
                hoy.plusDays(1)
                        .atStartOfDay()
                        .minusNanos(1);


        // =========================================
        // 4. Buscar comidas registradas hoy
        // =========================================

        List<AnalisisComida> comidas =
                analisisComidaRepository
                        .findByUsuarioIdAndFechaRegistroBetween(
                                usuario.getId(),
                                inicioDia,
                                finDia
                        );


        // =========================================
        // 5. Sumar nutrientes consumidos
        // =========================================

        double caloriasConsumidas = 0;
        double proteinasConsumidas = 0;
        double carbohidratosConsumidos = 0;
        double grasasConsumidas = 0;

        double fibraConsumida = 0;
        double azucaresConsumidos = 0;
        double sodioConsumido = 0;


        for (AnalisisComida comida : comidas) {

            caloriasConsumidas +=
                    valorSeguro(comida.getCaloriasTotales());

            proteinasConsumidas +=
                    valorSeguro(comida.getProteinasTotales());

            carbohidratosConsumidos +=
                    valorSeguro(comida.getCarbohidratosTotales());

            grasasConsumidas +=
                    valorSeguro(comida.getGrasasTotales());

            fibraConsumida +=
                    valorSeguro(comida.getFibraTotal());

            azucaresConsumidos +=
                    valorSeguro(comida.getAzucaresTotales());

            sodioConsumido +=
                    valorSeguro(comida.getSodioTotal());
        }


        // =========================================
        // 6. Obtener objetivos
        // =========================================

        Double caloriasObjetivo =
                objetivos.getCaloriasObjetivo();

        Double proteinasObjetivo =
                objetivos.getProteinasDiarias();

        Double carbohidratosObjetivo =
                objetivos.getCarbohidratosDiarios();

        Double grasasObjetivo =
                objetivos.getGrasasDiarias();


        // =========================================
        // 7. Calcular valores restantes
        // =========================================

        Double caloriasRestantes =
                calcularRestante(
                        caloriasObjetivo,
                        caloriasConsumidas
                );

        Double proteinasRestantes =
                calcularRestante(
                        proteinasObjetivo,
                        proteinasConsumidas
                );

        Double carbohidratosRestantes =
                calcularRestante(
                        carbohidratosObjetivo,
                        carbohidratosConsumidos
                );

        Double grasasRestantes =
                calcularRestante(
                        grasasObjetivo,
                        grasasConsumidas
                );


        // =========================================
        // 8. Construir respuesta
        // =========================================

        return ResumenNutricionalDiarioResponse.builder()

                .fecha(hoy)

                .comidasRegistradas(comidas.size())

                .caloriasObjetivo(
                        redondear(caloriasObjetivo))

                .caloriasConsumidas(
                        redondear(caloriasConsumidas))

                .caloriasRestantes(
                        redondear(caloriasRestantes))

                .proteinasObjetivo(
                        redondear(proteinasObjetivo))

                .proteinasConsumidas(
                        redondear(proteinasConsumidas))

                .proteinasRestantes(
                        redondear(proteinasRestantes))

                .carbohidratosObjetivo(
                        redondear(carbohidratosObjetivo))

                .carbohidratosConsumidos(
                        redondear(carbohidratosConsumidos))

                .carbohidratosRestantes(
                        redondear(carbohidratosRestantes))

                .grasasObjetivo(
                        redondear(grasasObjetivo))

                .grasasConsumidas(
                        redondear(grasasConsumidas))

                .grasasRestantes(
                        redondear(grasasRestantes))

                .fibraConsumida(
                        redondear(fibraConsumida))

                .azucaresConsumidos(
                        redondear(azucaresConsumidos))

                .sodioConsumido(
                        redondear(sodioConsumido))

                .build();
    }


    /**
     * Evita errores cuando algún valor nutricional
     * almacenado sea null.
     */
    private Double valorSeguro(Double valor) {

        return valor == null ? 0.0 : valor;
    }


    /**
     * Calcula cuánto falta para alcanzar un objetivo.
     *
     * Si el usuario supera el objetivo, el resultado
     * será negativo.
     */
    private Double calcularRestante(
            Double objetivo,
            Double consumido) {

        if (objetivo == null) {
            return null;
        }

        return objetivo - valorSeguro(consumido);
    }


    /**
     * Redondea los valores a dos decimales.
     */
    private Double redondear(Double valor) {

        if (valor == null) {
            return null;
        }

        return Math.round(valor * 100.0) / 100.0;
    }
}