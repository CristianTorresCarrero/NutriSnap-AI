package com.nutrisnap.service;

import com.nutrisnap.dto.ProgresoPesoResponse;
import com.nutrisnap.dto.RegistroPesoRequest;
import com.nutrisnap.dto.RegistroPesoResponse;

import java.util.List;

public interface RegistroPesoService {

    RegistroPesoResponse registrarPeso(
            String email,
            RegistroPesoRequest request
    );

    List<RegistroPesoResponse> obtenerHistorial(
            String email
    );

    ProgresoPesoResponse obtenerProgreso(
            String email
    );
}