package com.nutrisnap.service;

import com.nutrisnap.dto.PerfilResponse;
import com.nutrisnap.dto.PerfilUpdateRequest;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Servicio encargado de gestionar el perfil
 * del usuario autenticado.
 * -------------------------------------------------------
 */
public interface PerfilService {

    /**
     * Obtiene el perfil del usuario autenticado.
     *
     * @param email correo obtenido del JWT
     * @return información del perfil
     */
    PerfilResponse obtenerPerfil(String email);

    /**
     * Actualiza el perfil del usuario autenticado.
     *
     * @param email correo obtenido del JWT
     * @param request datos actualizados
     * @return perfil actualizado
     */
    PerfilResponse actualizarPerfil(
            String email,
            PerfilUpdateRequest request
    );

}