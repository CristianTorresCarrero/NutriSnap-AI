package com.nutrisnap.service.impl;

import com.nutrisnap.dto.AlimentoDetectadoResponse;
import com.nutrisnap.service.ReconocimientoAlimentosService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ReconocimientoAlimentosServiceImpl
        implements ReconocimientoAlimentosService {

    @Override
    public List<AlimentoDetectadoResponse> reconocerAlimentos(
            MultipartFile imagen) {

        /*
         * IMPORTANTE:
         * Estos datos son simulados temporalmente.
         *
         * Más adelante este método llamará al modelo de IA
         * que analizará realmente la imagen.
         */
        return List.of(
                AlimentoDetectadoResponse.builder()
                        .nombre("Arroz")
                        .confianza(0.94)
                        .build(),

                AlimentoDetectadoResponse.builder()
                        .nombre("Pechuga de pollo")
                        .confianza(0.89)
                        .build()
        );
    }
}