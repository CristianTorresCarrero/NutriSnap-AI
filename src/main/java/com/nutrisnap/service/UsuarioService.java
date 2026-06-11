package com.nutrisnap.service;

import com.nutrisnap.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 * Autor: Cristian Torres Carrero
 *
 * Define las operaciones relacionadas con los usuarios.
 * -------------------------------------------------------
 */

public interface UsuarioService {

    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    boolean existeEmail(String email);

}
