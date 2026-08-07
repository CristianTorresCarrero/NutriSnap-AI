package com.nutrisnap.service.impl;

import com.nutrisnap.dto.PerfilResponse;
import com.nutrisnap.dto.PerfilUpdateRequest;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Implementación de la gestión del perfil
 * del usuario autenticado.
 * -------------------------------------------------------
 */
@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public PerfilResponse obtenerPerfil(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        return PerfilResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .peso(usuario.getPeso())
                .altura(usuario.getAltura())
                .edad(usuario.getEdad())
                .sexo(usuario.getSexo())
                .objetivo(usuario.getObjetivo())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .build();
    }

    @Override
    public PerfilResponse actualizarPerfil(
            String email,
            PerfilUpdateRequest request) {

        // Buscar usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        // Actualizar datos
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPeso(request.getPeso());
        usuario.setAltura(request.getAltura());
        usuario.setEdad(request.getEdad());
        usuario.setSexo(request.getSexo());
        usuario.setObjetivo(request.getObjetivo());
        usuario.setFechaNacimiento(request.getFechaNacimiento());

        // Guardar cambios
        usuario = usuarioRepository.save(usuario);

        // Responder
        return PerfilResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .peso(usuario.getPeso())
                .altura(usuario.getAltura())
                .edad(usuario.getEdad())
                .sexo(usuario.getSexo())
                .objetivo(usuario.getObjetivo())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .build();
    }
}