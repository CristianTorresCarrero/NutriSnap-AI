package com.nutrisnap.service.impl;

import com.nutrisnap.dto.AlimentoDetectadoResponse;
import com.nutrisnap.dto.PrediccionIAResponse;
import com.nutrisnap.exception.ApiException;
import com.nutrisnap.service.ReconocimientoAlimentosService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ReconocimientoAlimentosServiceImpl
        implements ReconocimientoAlimentosService {

    private final RestTemplate restTemplate;
    private final String aiUrl;

    public ReconocimientoAlimentosServiceImpl(
            @Value("${nutrisnap.ai.url}") String aiUrl) {

        this.restTemplate = new RestTemplate();
        this.aiUrl = aiUrl;
    }

    @Override
    public List<AlimentoDetectadoResponse> reconocerAlimentos(
            MultipartFile imagen) {

        try {

            ByteArrayResource recurso =
                    new ByteArrayResource(imagen.getBytes()) {

                        @Override
                        public String getFilename() {
                            return imagen.getOriginalFilename();
                        }
                    };

            // Headers de la imagen
            HttpHeaders headersImagen =
                    new HttpHeaders();

            headersImagen.setContentType(
                    MediaType.parseMediaType(
                            imagen.getContentType()
                    )
            );

            // Parte "imagen"
            HttpEntity<ByteArrayResource> parteImagen =
                    new HttpEntity<>(
                            recurso,
                            headersImagen
                    );

            // Multipart
            MultiValueMap<String, Object> body =
                    new LinkedMultiValueMap<>();

            body.add(
                    "imagen",
                    parteImagen
            );

            // Headers de la petición completa
            HttpHeaders headersPeticion =
                    new HttpHeaders();

            headersPeticion.setContentType(
                    MediaType.MULTIPART_FORM_DATA
            );

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(
                            body,
                            headersPeticion
                    );

            ResponseEntity<PrediccionIAResponse> response =
                    restTemplate.postForEntity(
                            aiUrl + "/predict",
                            request,
                            PrediccionIAResponse.class
                    );

            PrediccionIAResponse respuesta =
                    response.getBody();

            if (respuesta == null) {
                throw new ApiException(
                        "El servicio de IA no devolvió una respuesta."
                );
            }

            if (respuesta.getAlimentosDetectados() == null) {
                return List.of();
            }

            return respuesta.getAlimentosDetectados();

        } catch (IOException ex) {

            throw new ApiException(
                    "No fue posible procesar la imagen."
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            throw new ApiException(
                    "No fue posible comunicarse con el servicio de IA."
            );
        }
    }
}