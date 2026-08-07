package com.nutrisnap.service;

import com.nutrisnap.dto.*;
import com.nutrisnap.enums.CategoriaAlimento;

import java.util.List;

public interface AlimentoService {

    AlimentoResponse registrarAlimento(AlimentoRequest request);

    List<AlimentoResponse> listarAlimentos();

    AlimentoResponse buscarPorId(Long id);

    AlimentoResponse buscarPorNombre(String nombre);

    AlimentoResponse actualizarAlimento(Long id, AlimentoRequest request);

    AlimentoResponse desactivarAlimento(Long id);

    List<AlimentoResponse> buscarPorNombreParcial(String nombre);

    List<AlimentoResponse> buscarPorCategoria(
            CategoriaAlimento categoria
    );

    PorcionNutricionalResponse calcularPorcion(
            Long alimentoId,
            Double cantidadGramos
    );

    CalculoComidaResponse calcularComida(CalculoComidaRequest request);
}