package com.nutrisnap.controller;

import com.nutrisnap.dto.AlimentoRequest;
import com.nutrisnap.dto.AlimentoResponse;
import com.nutrisnap.enums.CategoriaAlimento;
import com.nutrisnap.service.AlimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador encargado de gestionar los alimentos
 * disponibles en la base nutricional.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api/alimentos")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    /**
     * Registra un nuevo alimento.
     */
    @PostMapping
    public AlimentoResponse registrar(
            @Valid @RequestBody AlimentoRequest request) {

        return alimentoService.registrarAlimento(request);
    }

    /**
     * Lista todos los alimentos activos.
     */
    @GetMapping
    public List<AlimentoResponse> listar() {

        return alimentoService.listarAlimentos();
    }

    /**
     * Busca un alimento por su ID.
     */
    @GetMapping("/{id}")
    public AlimentoResponse buscarPorId(
            @PathVariable Long id) {

        return alimentoService.buscarPorId(id);
    }

    /**
     * Busca un alimento por nombre.
     */
    @GetMapping("/buscar")
    public AlimentoResponse buscarPorNombre(
            @RequestParam String nombre) {

        return alimentoService.buscarPorNombre(nombre);
    }

    @PutMapping("/{id}")
    public AlimentoResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlimentoRequest request) {

        return alimentoService.actualizarAlimento(
                id,
                request
        );
    }

    @PatchMapping("/{id}/desactivar")
    public AlimentoResponse desactivar(
            @PathVariable Long id) {

        return alimentoService.desactivarAlimento(id);
    }

    @GetMapping("/buscar-parcial")
    public List<AlimentoResponse> buscarPorNombreParcial(
            @RequestParam String nombre) {

        return alimentoService.buscarPorNombreParcial(nombre);
    }

    @GetMapping("/categoria/{categoria}")
    public List<AlimentoResponse> buscarPorCategoria(
            @PathVariable CategoriaAlimento categoria) {

        return alimentoService.buscarPorCategoria(categoria);
    }
}