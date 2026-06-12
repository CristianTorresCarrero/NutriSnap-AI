package com.nutrisnap.controller;

import com.nutrisnap.dto.UsuarioRequest;
import com.nutrisnap.dto.UsuarioResponse;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI

 * Controlador encargado de gestionar las operaciones
 * relacionadas con los usuarios.
 * -------------------------------------------------------
 */

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    /**
     * Registrar un nuevo usuario.
     */

    @PostMapping("/registro")
    public UsuarioResponse registrarUsuario(@Valid @RequestBody UsuarioRequest request){
        return usuarioService.registrarUsuario(request);
    }

    /**
     * Obtener todos los usuarios.
     */
    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

}
