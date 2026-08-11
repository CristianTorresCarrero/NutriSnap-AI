package com.nutrisnap.service;

import com.nutrisnap.dto.AnalisisComidaResponse;
import com.nutrisnap.dto.CalculoComidaRequest;

import java.time.LocalDate;
import java.util.List;

public interface AnalisisComidaService {

    AnalisisComidaResponse calcularYGuardar(
            String email,
            CalculoComidaRequest request
    );

    List<AnalisisComidaResponse> obtenerHistorial(
            String email
    );

    List<AnalisisComidaResponse> obtenerHistorialPorFecha(
            String email,
            LocalDate fecha
    );
}