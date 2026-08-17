package com.nutrisnap.service.impl;

import com.nutrisnap.dto.ProgresoPesoResponse;
import com.nutrisnap.dto.RegistroPesoRequest;
import com.nutrisnap.dto.RegistroPesoResponse;
import com.nutrisnap.entity.RegistroPeso;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.RegistroPesoRepository;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.RegistroPesoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroPesoServiceImpl
        implements RegistroPesoService {

    private final RegistroPesoRepository registroPesoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public RegistroPesoResponse registrarPeso(
            String email,
            RegistroPesoRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        RegistroPeso registro = RegistroPeso.builder()
                .usuario(usuario)
                .peso(request.getPeso())
                .fechaRegistro(LocalDateTime.now())
                .build();

        registro = registroPesoRepository.save(registro);

        return convertirAResponse(registro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroPesoResponse> obtenerHistorial(
            String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        return registroPesoRepository
                .findByUsuarioIdOrderByFechaRegistroDesc(
                        usuario.getId())
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProgresoPesoResponse obtenerProgreso(
            String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        List<RegistroPeso> registros =
                registroPesoRepository
                        .findByUsuarioIdOrderByFechaRegistroDesc(
                                usuario.getId());

        if (registros.isEmpty()) {

            return ProgresoPesoResponse.builder()
                    .pesoActual(null)
                    .pesoAnterior(null)
                    .cambioPeso(null)
                    .totalRegistros(0)
                    .build();
        }

        Double pesoActual =
                registros.get(0).getPeso();

        Double pesoAnterior = null;
        Double cambioPeso = null;

        if (registros.size() >= 2) {

            pesoAnterior =
                    registros.get(1).getPeso();

            cambioPeso =
                    pesoActual - pesoAnterior;
        }

        return ProgresoPesoResponse.builder()
                .pesoActual(redondear(pesoActual))
                .pesoAnterior(redondear(pesoAnterior))
                .cambioPeso(redondear(cambioPeso))
                .totalRegistros(registros.size())
                .build();
    }

    private RegistroPesoResponse convertirAResponse(
            RegistroPeso registro) {

        return RegistroPesoResponse.builder()
                .id(registro.getId())
                .peso(registro.getPeso())
                .fechaRegistro(registro.getFechaRegistro())
                .build();
    }

    private Double redondear(Double valor) {

        if (valor == null) {
            return null;
        }

        return Math.round(valor * 100.0) / 100.0;
    }
}