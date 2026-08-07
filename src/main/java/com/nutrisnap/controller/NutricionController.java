package com.nutrisnap.controller;

import com.nutrisnap.dto.CalculoNutricionalResponse;
import com.nutrisnap.service.NutricionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nutricion")
@RequiredArgsConstructor
public class NutricionController {

    private final NutricionService nutricionService;

    @GetMapping("/calcular")
    public CalculoNutricionalResponse calcular(
            Authentication authentication) {

        return nutricionService.calcularNutricion(
                authentication.getName()
        );
    }
}