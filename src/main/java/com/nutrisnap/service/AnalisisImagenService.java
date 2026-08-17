package com.nutrisnap.service;

import com.nutrisnap.dto.AnalisisImagenResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AnalisisImagenService {

    AnalisisImagenResponse procesarImagen(MultipartFile imagen);
}