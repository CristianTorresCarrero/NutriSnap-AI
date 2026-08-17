package com.nutrisnap.service.impl;

import com.nutrisnap.dto.AnalisisImagenResponse;
import com.nutrisnap.exception.ApiException;
import com.nutrisnap.service.AnalisisImagenService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class AnalisisImagenServiceImpl implements AnalisisImagenService {

    private static final long TAMANO_MAXIMO =
            5 * 1024 * 1024;

    private static final Set<String> TIPOS_PERMITIDOS =
            Set.of(
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE
            );

    @Override
    public AnalisisImagenResponse procesarImagen(
            MultipartFile imagen) {

        validarImagen(imagen);

        return AnalisisImagenResponse.builder()
                .success(true)
                .nombreArchivo(imagen.getOriginalFilename())
                .tipoContenido(imagen.getContentType())
                .tamanoBytes(imagen.getSize())
                .mensaje("Imagen recibida correctamente.")
                .build();
    }

    private void validarImagen(MultipartFile imagen) {

        if (imagen == null || imagen.isEmpty()) {
            throw new ApiException(
                    "La imagen no puede estar vacía."
            );
        }

        String tipoContenido = imagen.getContentType();

        if (tipoContenido == null ||
                !TIPOS_PERMITIDOS.contains(tipoContenido)) {

            throw new ApiException(
                    "Formato de imagen no permitido. Solo se aceptan JPG, JPEG y PNG."
            );
        }

        if (imagen.getSize() > TAMANO_MAXIMO) {
            throw new ApiException(
                    "La imagen no puede superar los 5 MB."
            );
        }
    }
}