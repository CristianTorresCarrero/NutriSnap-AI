package com.nutrisnap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalisisImagenResponse {

    private boolean success;

    private String nombreArchivo;

    private String tipoContenido;

    private long tamanoBytes;

    private String mensaje;

    private List<AlimentoDetectadoResponse> alimentosDetectados;
}