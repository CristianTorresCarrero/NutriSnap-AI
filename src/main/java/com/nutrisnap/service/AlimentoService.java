package com.nutrisnap.service;

import com.nutrisnap.dto.AlimentoRequest;
import com.nutrisnap.dto.AlimentoResponse;

import java.util.List;

public interface AlimentoService {

    AlimentoResponse registrarAlimento(AlimentoRequest request);

    List<AlimentoResponse> listarAlimentos();

    AlimentoResponse buscarPorId(Long id);

    AlimentoResponse buscarPorNombre(String nombre);
}