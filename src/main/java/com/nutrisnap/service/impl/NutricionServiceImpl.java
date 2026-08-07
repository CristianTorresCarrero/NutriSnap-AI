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

        // Buscar el usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario"));

        Double imc = calcularIMC(
                usuario.getPeso(),
                usuario.getAltura()
        );

        String clasificacion = clasificarIMC(imc);

        Double tmb = calcularTMB(usuario);

        Double caloriasDiarias = calcularCaloriasDiarias(
                tmb,
                usuario.getNivelActividad()
        );

        Double aguaDiaria = calcularAguaDiaria(
                usuario.getPeso()
        );

        // Construir respuesta
        return CalculoNutricionalResponse.builder()
                .imc(redondear(imc))
                .clasificacionIMC(clasificacion)
                .tmb(redondear(tmb))
                .caloriasDiarias(redondear(caloriasDiarias))
                .aguaDiaria(redondear(aguaDiaria))
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

    private Double calcularTMB(Usuario usuario) {

        if (usuario.getPeso() == null ||
                usuario.getAltura() == null ||
                usuario.getEdad() == null ||
                usuario.getSexo() == null) {

            return null;
        }

        double alturaCm = usuario.getAltura() * 100;

        if (usuario.getSexo().equalsIgnoreCase("Masculino")) {

            return (10 * usuario.getPeso())
                    + (6.25 * alturaCm)
                    - (5 * usuario.getEdad())
                    + 5;

        }

        if (usuario.getSexo().equalsIgnoreCase("Femenino")) {

            return (10 * usuario.getPeso())
                    + (6.25 * alturaCm)
                    - (5 * usuario.getEdad())
                    - 161;

        }

        return null;
    }

    private Double obtenerFactorActividad(String nivelActividad) {

        if (nivelActividad == null) {
            return null;
        }

        return switch (nivelActividad.toUpperCase()) {
            case "SEDENTARIO" -> 1.20;
            case "LIGERO" -> 1.375;
            case "MODERADO" -> 1.55;
            case "INTENSO" -> 1.725;
            case "MUY_INTENSO" -> 1.90;
            default -> null;
        };
    }

    private Double calcularCaloriasDiarias(
            Double tmb,
            String nivelActividad) {

        if (tmb == null) {
            return null;
        }

        Double factorActividad =
                obtenerFactorActividad(nivelActividad);

        if (factorActividad == null) {
            return null;
        }

        return tmb * factorActividad;
    }

    private Double calcularAguaDiaria(Double peso) {

        if (peso == null || peso <= 0) {
            return null;
        }

        double litros = (peso * 35) / 1000;

        return litros;
    }
}