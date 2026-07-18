package com.nutrisnap.service.impl;


import com.nutrisnap.dto.UsuarioRequest;
import com.nutrisnap.dto.UsuarioResponse;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.EmailAlreadyExistsException;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public UsuarioResponse registrarUsuario(UsuarioRequest request){

        if(usuarioRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException();
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol("USER")
                .estado(true)
                .fechaRegistro(java.time.LocalDateTime.now())
                .build();

        usuario = usuarioRepository.save(usuario);

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .mensaje("Usuario registrado correctamente.")
                .build();
    }

}
