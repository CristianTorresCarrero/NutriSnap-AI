package com.nutrisnap.service;

import com.nutrisnap.dto.UsuarioRequest;
import com.nutrisnap.dto.UsuarioResponse;
import com.nutrisnap.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
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
    UsuarioResponse registrarUsuario(UsuarioRequest request);

}
