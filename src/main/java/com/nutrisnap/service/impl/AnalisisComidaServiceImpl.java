package com.nutrisnap.service.impl;

import com.nutrisnap.dto.*;
import com.nutrisnap.entity.Alimento;
import com.nutrisnap.entity.AnalisisComida;
import com.nutrisnap.entity.DetalleAnalisisComida;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.AlimentoRepository;
import com.nutrisnap.repository.AnalisisComidaRepository;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.AlimentoService;
import com.nutrisnap.service.AnalisisComidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalisisComidaServiceImpl
        implements AnalisisComidaService {

    private final AnalisisComidaRepository analisisComidaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AlimentoRepository alimentoRepository;
    private final AlimentoService alimentoService;

    @Override
    @Transactional
    public AnalisisComidaResponse calcularYGuardar(
            String email,
            CalculoComidaRequest request) {

        // 1. Buscar al usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        // 2. Reutilizar el motor de cálculo de comidas
        CalculoComidaResponse calculo =
                alimentoService.calcularComida(request);

        // 3. Crear el análisis principal
        AnalisisComida analisis = AnalisisComida.builder()
                .usuario(usuario)
                .tipoComida(request.getTipoComida())
                .caloriasTotales(calculo.getCaloriasTotales())
                .proteinasTotales(calculo.getProteinasTotales())
                .carbohidratosTotales(calculo.getCarbohidratosTotales())
                .grasasTotales(calculo.getGrasasTotales())
                .fibraTotal(calculo.getFibraTotal())
                .azucaresTotales(calculo.getAzucaresTotales())
                .sodioTotal(calculo.getSodioTotal())
                .fechaRegistro(LocalDateTime.now())
                .detalles(new ArrayList<>())
                .build();

        // 4. Crear cada detalle de la comida
        for (PorcionNutricionalResponse porcion
                : calculo.getAlimentos()) {

            Alimento alimento = alimentoRepository
                    .findById(porcion.getAlimentoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Alimento"));

            DetalleAnalisisComida detalle =
                    DetalleAnalisisComida.builder()
                            .analisisComida(analisis)
                            .alimento(alimento)
                            .cantidadGramos(
                                    porcion.getCantidadGramos())
                            .calorias(porcion.getCalorias())
                            .proteinas(porcion.getProteinas())
                            .carbohidratos(
                                    porcion.getCarbohidratos())
                            .grasas(porcion.getGrasas())
                            .fibra(porcion.getFibra())
                            .azucares(porcion.getAzucares())
                            .sodio(porcion.getSodio())
                            .build();

            analisis.getDetalles().add(detalle);
        }

        // 5. Guardar análisis + detalles gracias a CascadeType.ALL
        analisis = analisisComidaRepository.save(analisis);

        // 6. Convertir a DTO
        return convertirAResponse(analisis);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisComidaResponse> obtenerHistorial(
            String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        return analisisComidaRepository
                .findByUsuarioIdOrderByFechaRegistroDesc(
                        usuario.getId())
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    private AnalisisComidaResponse convertirAResponse(
            AnalisisComida analisis) {

        List<DetalleAnalisisResponse> detalles =
                analisis.getDetalles()
                        .stream()
                        .map(detalle ->
                                DetalleAnalisisResponse.builder()
                                        .alimentoId(
                                                detalle.getAlimento().getId())
                                        .nombreAlimento(
                                                detalle.getAlimento().getNombre())
                                        .cantidadGramos(
                                                detalle.getCantidadGramos())
                                        .calorias(detalle.getCalorias())
                                        .proteinas(detalle.getProteinas())
                                        .carbohidratos(
                                                detalle.getCarbohidratos())
                                        .grasas(detalle.getGrasas())
                                        .fibra(detalle.getFibra())
                                        .azucares(detalle.getAzucares())
                                        .sodio(detalle.getSodio())
                                        .build()
                        )
                        .toList();

        return AnalisisComidaResponse.builder()
                .id(analisis.getId())
                .fechaRegistro(analisis.getFechaRegistro())
                .tipoComida(analisis.getTipoComida())
                .caloriasTotales(analisis.getCaloriasTotales())
                .proteinasTotales(analisis.getProteinasTotales())
                .carbohidratosTotales(
                        analisis.getCarbohidratosTotales())
                .grasasTotales(analisis.getGrasasTotales())
                .fibraTotal(analisis.getFibraTotal())
                .azucaresTotales(analisis.getAzucaresTotales())
                .sodioTotal(analisis.getSodioTotal())
                .alimentos(detalles)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisComidaResponse> obtenerHistorialPorFecha(
            String email,
            LocalDate fecha) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        LocalDateTime inicioDia =
                fecha.atStartOfDay();

        LocalDateTime finDia =
                fecha.plusDays(1)
                        .atStartOfDay()
                        .minusNanos(1);

        return analisisComidaRepository
                .findByUsuarioIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(
                        usuario.getId(),
                        inicioDia,
                        finDia
                )
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }
}