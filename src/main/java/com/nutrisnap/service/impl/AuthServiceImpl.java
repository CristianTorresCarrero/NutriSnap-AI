package com.nutrisnap.service.impl;

import com.nutrisnap.dto.LoginRequest;
import com.nutrisnap.dto.LoginResponse;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.security.JwtService;
import com.nutrisnap.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        // Spring Security valida usuario y contraseña
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Obtener usuario desde la base de datos
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        // Generar JWT
        String token = jwtService.generateToken(usuario.getEmail());

        // Respuesta
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(3600L)
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .build();
    }
}