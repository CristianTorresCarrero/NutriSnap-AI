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
public class PrediccionIAResponse {

    private boolean success;

    private String nombreArchivo;

    private String tipoContenido;

    private Long tamanoBytes;

    private List<AlimentoDetectadoResponse> alimentosDetectados;
}