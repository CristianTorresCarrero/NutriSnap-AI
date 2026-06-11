package com.nutrisnap.service.impl;


import com.nutrisnap.entity.Usuario;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * -------------------------------------------------------
 * Implementación de la lógica de negocio de usuarios.
 * -------------------------------------------------------
 */

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public boolean existeEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }
}
