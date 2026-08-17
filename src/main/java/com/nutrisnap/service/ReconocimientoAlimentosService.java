package com.nutrisnap.service;

import com.nutrisnap.dto.AlimentoDetectadoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReconocimientoAlimentosService {

    List<AlimentoDetectadoResponse> reconocerAlimentos(
            MultipartFile imagen
    );
}