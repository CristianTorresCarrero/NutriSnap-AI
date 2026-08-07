package com.nutrisnap.service.impl;

import com.nutrisnap.dto.AlimentoRequest;
import com.nutrisnap.dto.AlimentoResponse;
import com.nutrisnap.dto.PorcionNutricionalResponse;
import com.nutrisnap.entity.Alimento;
import com.nutrisnap.enums.CategoriaAlimento;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.AlimentoRepository;
import com.nutrisnap.service.AlimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Implementación de la lógica de negocio relacionada
 * con los alimentos y su información nutricional.
 * -------------------------------------------------------
 */
@Service
@RequiredArgsConstructor
public class AlimentoServiceImpl implements AlimentoService {

    private final AlimentoRepository alimentoRepository;

    /**
     * Registra un nuevo alimento.
     */
    @Override
    public AlimentoResponse registrarAlimento(AlimentoRequest request) {

        // Evitar alimentos duplicados por nombre
        if (alimentoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un alimento registrado con ese nombre."
            );
        }

        Alimento alimento = Alimento.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .categoria(request.getCategoria())
                .caloriasPor100g(request.getCaloriasPor100g())
                .proteinasPor100g(request.getProteinasPor100g())
                .carbohidratosPor100g(request.getCarbohidratosPor100g())
                .grasasPor100g(request.getGrasasPor100g())
                .fibraPor100g(request.getFibraPor100g())
                .azucaresPor100g(request.getAzucaresPor100g())
                .sodioPor100g(request.getSodioPor100g())
                .activo(true)
                .fechaRegistro(LocalDateTime.now())
                .build();

        alimento = alimentoRepository.save(alimento);

        return convertirAResponse(alimento);
    }

    /**
     * Lista únicamente los alimentos activos.
     */
    @Override
    public List<AlimentoResponse> listarAlimentos() {

        return alimentoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    /**
     * Busca un alimento por su ID.
     */
    @Override
    public AlimentoResponse buscarPorId(Long id) {

        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Alimento"));

        return convertirAResponse(alimento);
    }

    /**
     * Busca un alimento por su nombre ignorando
     * diferencias entre mayúsculas y minúsculas.
     */
    @Override
    public AlimentoResponse buscarPorNombre(String nombre) {

        Alimento alimento = alimentoRepository
                .findByNombreIgnoreCase(nombre)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Alimento"));

        return convertirAResponse(alimento);
    }

    /**
     * Convierte la entidad Alimento a AlimentoResponse.
     *
     * Esto evita repetir el mismo builder en todos
     * los métodos del servicio.
     */
    private AlimentoResponse convertirAResponse(Alimento alimento) {

        return AlimentoResponse.builder()
                .id(alimento.getId())
                .nombre(alimento.getNombre())
                .descripcion(alimento.getDescripcion())
                .categoria(alimento.getCategoria())
                .caloriasPor100g(alimento.getCaloriasPor100g())
                .proteinasPor100g(alimento.getProteinasPor100g())
                .carbohidratosPor100g(alimento.getCarbohidratosPor100g())
                .grasasPor100g(alimento.getGrasasPor100g())
                .fibraPor100g(alimento.getFibraPor100g())
                .azucaresPor100g(alimento.getAzucaresPor100g())
                .sodioPor100g(alimento.getSodioPor100g())
                .activo(alimento.getActivo())
                .fechaRegistro(alimento.getFechaRegistro())
                .build();
    }

    @Override
    public AlimentoResponse actualizarAlimento(
            Long id,
            AlimentoRequest request) {

        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Alimento"));

        // Si cambia el nombre, evitar duplicados
        alimentoRepository.findByNombreIgnoreCase(request.getNombre())
                .ifPresent(alimentoExistente -> {

                    if (!alimentoExistente.getId().equals(id)) {
                        throw new IllegalArgumentException(
                                "Ya existe un alimento registrado con ese nombre."
                        );
                    }
                });

        alimento.setNombre(request.getNombre());
        alimento.setDescripcion(request.getDescripcion());
        alimento.setCategoria(request.getCategoria());

        alimento.setCaloriasPor100g(
                request.getCaloriasPor100g()
        );

        alimento.setProteinasPor100g(
                request.getProteinasPor100g()
        );

        alimento.setCarbohidratosPor100g(
                request.getCarbohidratosPor100g()
        );

        alimento.setGrasasPor100g(
                request.getGrasasPor100g()
        );

        alimento.setFibraPor100g(
                request.getFibraPor100g()
        );

        alimento.setAzucaresPor100g(
                request.getAzucaresPor100g()
        );

        alimento.setSodioPor100g(
                request.getSodioPor100g()
        );

        alimento = alimentoRepository.save(alimento);

        return convertirAResponse(alimento);
    }

    @Override
    public AlimentoResponse desactivarAlimento(Long id) {

        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Alimento"));

        if (Boolean.FALSE.equals(alimento.getActivo())) {
            throw new IllegalArgumentException(
                    "El alimento ya se encuentra desactivado."
            );
        }

        alimento.setActivo(false);

        alimento = alimentoRepository.save(alimento);

        return convertirAResponse(alimento);
    }

    @Override
    public List<AlimentoResponse> buscarPorNombreParcial(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "Debe ingresar un nombre para realizar la búsqueda."
            );
        }

        return alimentoRepository
                .findByNombreContainingIgnoreCaseAndActivoTrue(nombre.trim())
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public List<AlimentoResponse> buscarPorCategoria(
            CategoriaAlimento categoria) {

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "La categoría es obligatoria."
            );
        }

        return alimentoRepository
                .findByCategoriaAndActivoTrue(categoria)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    public PorcionNutricionalResponse calcularPorcion(
            Long alimentoId,
            Double cantidadGramos) {

        if (cantidadGramos == null || cantidadGramos <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad en gramos debe ser mayor que cero."
            );
        }

        Alimento alimento = alimentoRepository.findById(alimentoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Alimento"));

        if (Boolean.FALSE.equals(alimento.getActivo())) {
            throw new IllegalArgumentException(
                    "El alimento se encuentra desactivado."
            );
        }

        double factor = cantidadGramos / 100.0;

        return PorcionNutricionalResponse.builder()
                .alimentoId(alimento.getId())
                .nombre(alimento.getNombre())
                .cantidadGramos(cantidadGramos)
                .calorias(redondear(alimento.getCaloriasPor100g() * factor))
                .proteinas(redondear(alimento.getProteinasPor100g() * factor))
                .carbohidratos(redondear(alimento.getCarbohidratosPor100g() * factor))
                .grasas(redondear(alimento.getGrasasPor100g() * factor))
                .fibra(calcularOpcional(alimento.getFibraPor100g(), factor))
                .azucares(calcularOpcional(alimento.getAzucaresPor100g(), factor))
                .sodio(calcularOpcional(alimento.getSodioPor100g(), factor))
                .build();
    }

    private Double calcularOpcional(Double valorPor100g, double factor) {

        if (valorPor100g == null) {
            return null;
        }

        return redondear(valorPor100g * factor);
    }

    private Double redondear(Double valor) {

        if (valor == null) {
            return null;
        }

        return Math.round(valor * 100.0) / 100.0;
    }
}