package com.nutrisnap.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * -------------------------------------------------------
 * Proyecto: NutriSnap AI
 *
 * Controlador temporal para verificar la autenticación JWT.
 * -------------------------------------------------------
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Ruta protegida funcionando";
    }

}