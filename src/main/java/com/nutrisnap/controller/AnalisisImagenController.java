package com.nutrisnap.controller;

import com.nutrisnap.dto.AnalisisImagenResponse;
import com.nutrisnap.service.AnalisisImagenService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/analisis-imagen")
public class AnalisisImagenController {

    private final AnalisisImagenService analisisImagenService;

    public AnalisisImagenController(
            AnalisisImagenService analisisImagenService) {

        this.analisisImagenService =
                analisisImagenService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AnalisisImagenResponse> analizarImagen(
            @RequestParam("imagen") MultipartFile imagen) {

        return ResponseEntity.ok(
                analisisImagenService.procesarImagen(imagen)
        );
    }
}