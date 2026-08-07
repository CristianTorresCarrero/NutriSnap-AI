package com.nutrisnap.service.impl;

import com.nutrisnap.dto.CalculoNutricionalResponse;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.exception.ResourceNotFoundException;
import com.nutrisnap.repository.UsuarioRepository;
import com.nutrisnap.service.NutricionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutricionServiceImpl implements NutricionService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public CalculoNutricionalResponse calcularNutricion(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        Double imc = calcularIMC(
                usuario.getPeso(),
                usuario.getAltura()
        );

        String clasificacion = clasificarIMC(imc);

        return CalculoNutricionalResponse.builder()
                .imc(redondear(imc))
                .clasificacionIMC(clasificacion)
                .build();
    }

    private Double calcularIMC(Double peso, Double altura) {

        if (peso == null || altura == null || altura <= 0) {
            return null;
        }

        return peso / Math.pow(altura, 2);
    }

    private String clasificarIMC(Double imc) {

        if (imc == null) {
            return "Información insuficiente";
        }

        if (imc < 18.5) {
            return "Bajo peso";
        }

        if (imc < 25) {
            return "Peso normal";
        }

        if (imc < 30) {
            return "Sobrepeso";
        }

        if (imc < 35) {
            return "Obesidad grado I";
        }

        if (imc < 40) {
            return "Obesidad grado II";
        }

        return "Obesidad grado III";
    }

    private Double redondear(Double valor) {

        if (valor == null) {
            return null;
        }

        return Math.round(valor * 100.0) / 100.0;
    }
}