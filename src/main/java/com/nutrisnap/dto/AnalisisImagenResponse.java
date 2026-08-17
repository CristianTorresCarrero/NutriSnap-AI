package com.nutrisnap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}