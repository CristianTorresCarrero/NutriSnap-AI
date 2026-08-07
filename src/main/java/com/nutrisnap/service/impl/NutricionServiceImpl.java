package com.nutrisnap.service.impl;

import com.nutrisnap.dto.CalculoNutricionalResponse;
import com.nutrisnap.entity.Usuario;
import com.nutrisnap.enums.NivelActividad;
import com.nutrisnap.enums.ObjetivoNutricional;
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
        validarDatosNutricionales(usuario);

        Double imc = calcularIMC(
                usuario.getPeso(),
                usuario.getAltura()
        );

        String clasificacion = clasificarIMC(imc);

        Double tmb = calcularTMB(usuario);

        Double caloriasDiarias = calcularCaloriasDiarias(
                tmb,
                usuario.getNivelActividad(),
                usuario.getObjetivo()
        );

        Double aguaDiaria = calcularAguaDiaria(
                usuario.getPeso()
        );

        Double caloriasMantenimiento =
                calcularCaloriasMantenimiento(
                        tmb,
                        usuario.getNivelActividad()
                );

        Double caloriasObjetivo =
                calcularCaloriasObjetivo(
                        caloriasMantenimiento,
                        usuario.getObjetivo()
                );

        Double proteinasDiarias =
                calcularProteinasDiarias(
                        usuario.getPeso(),
                        usuario.getObjetivo()
                );

        Double grasasDiarias =
                calcularGrasasDiarias(caloriasObjetivo);

        Double carbohidratosDiarios =
                calcularCarbohidratosDiarios(
                        caloriasObjetivo,
                        proteinasDiarias,
                        grasasDiarias
                );

        Double porcentajeAjuste =
                obtenerPorcentajeAjuste(usuario.getObjetivo());

        // Construir respuesta
        return CalculoNutricionalResponse.builder()
                .imc(redondear(imc))
                .clasificacionIMC(clasificacion)
                .tmb(redondear(tmb))
                .caloriasMantenimiento(redondear(caloriasMantenimiento))
                .caloriasObjetivo(redondear(caloriasObjetivo))
                .proteinasDiarias(redondear(proteinasDiarias))
                .grasasDiarias(redondear(grasasDiarias))
                .carbohidratosDiarios(redondear(carbohidratosDiarios))
                .aguaDiaria(redondear(aguaDiaria))
                .porcentajeAjuste(porcentajeAjuste)
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

    private Double obtenerFactorActividad(NivelActividad nivelActividad) {

        if (nivelActividad == null) {
            return null;
        }

        return switch (nivelActividad) {
            case SEDENTARIO -> 1.20;
            case LIGERO -> 1.375;
            case MODERADO -> 1.55;
            case INTENSO -> 1.725;
            case MUY_INTENSO -> 1.90;
        };
    }

    private Double calcularCaloriasDiarias(
            Double tmb,
            NivelActividad nivelActividad,
            ObjetivoNutricional objetivo) {

        if (tmb == null || nivelActividad == null || objetivo == null) {
            return null;
        }

        Double factorActividad =
                obtenerFactorActividad(nivelActividad);

        if (factorActividad == null) {
            return null;
        }

        double caloriasMantenimiento =
                tmb * factorActividad;

        return switch (objetivo) {
            case BAJAR_PESO -> caloriasMantenimiento - 500;
            case MANTENER_PESO -> caloriasMantenimiento;
            case GANAR_PESO -> caloriasMantenimiento + 300;
        };
    }

    private Double calcularAguaDiaria(Double peso) {

        if (peso == null || peso <= 0) {
            return null;
        }

        double litros = (peso * 35) / 1000;

        return litros;
    }

    private Double calcularCaloriasMantenimiento(
            Double tmb,
            NivelActividad nivelActividad) {

        if (tmb == null || nivelActividad == null) {
            return null;
        }

        Double factorActividad =
                obtenerFactorActividad(nivelActividad);

        if (factorActividad == null) {
            return null;
        }

        return tmb * factorActividad;
    }

    private Double calcularCaloriasObjetivo(
            Double caloriasMantenimiento,
            ObjetivoNutricional objetivo) {

        if (caloriasMantenimiento == null || objetivo == null) {
            return null;
        }

        Double porcentajeAjuste =
                obtenerPorcentajeAjuste(objetivo);

        if (porcentajeAjuste == null) {
            return null;
        }

        return caloriasMantenimiento
                * (1 + porcentajeAjuste / 100);
    }

    private Double calcularProteinasDiarias(
            Double peso,
            ObjetivoNutricional objetivo) {

        if (peso == null || peso <= 0 || objetivo == null) {
            return null;
        }

        double gramosPorKg = switch (objetivo) {
            case BAJAR_PESO -> 1.8;
            case MANTENER_PESO -> 1.6;
            case GANAR_PESO -> 2.0;
        };

        return peso * gramosPorKg;
    }

    private Double calcularGrasasDiarias(Double caloriasObjetivo) {

        if (caloriasObjetivo == null || caloriasObjetivo <= 0) {
            return null;
        }

        double porcentajeGrasas = 0.25;

        return (caloriasObjetivo * porcentajeGrasas) / 9;
    }

    private Double calcularCarbohidratosDiarios(
            Double caloriasObjetivo,
            Double proteinasDiarias,
            Double grasasDiarias) {

        if (caloriasObjetivo == null ||
                proteinasDiarias == null ||
                grasasDiarias == null) {
            return null;
        }

        double caloriasProteinas = proteinasDiarias * 4;
        double caloriasGrasas = grasasDiarias * 9;

        double caloriasRestantes =
                caloriasObjetivo
                        - caloriasProteinas
                        - caloriasGrasas;

        if (caloriasRestantes <= 0) {
            return 0.0;
        }

        return caloriasRestantes / 4;
    }

    private void validarDatosNutricionales(Usuario usuario) {

        if (usuario.getPeso() == null || usuario.getPeso() <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe estar registrado y ser mayor que cero."
            );
        }

        if (usuario.getAltura() == null || usuario.getAltura() <= 0) {
            throw new IllegalArgumentException(
                    "La altura debe estar registrada y ser mayor que cero."
            );
        }

        if (usuario.getEdad() == null || usuario.getEdad() <= 0) {
            throw new IllegalArgumentException(
                    "La edad debe estar registrada y ser mayor que cero."
            );
        }

        if (usuario.getSexo() == null || usuario.getSexo().isBlank()) {
            throw new IllegalArgumentException(
                    "El sexo debe estar registrado."
            );
        }

        if (usuario.getObjetivo() == null) {
            throw new IllegalArgumentException(
                    "El objetivo nutricional debe estar registrado."
            );
        }

        if (usuario.getNivelActividad() == null) {
            throw new IllegalArgumentException(
                    "El nivel de actividad debe estar registrado."
            );
        }
    }

    private Double obtenerPorcentajeAjuste(
            ObjetivoNutricional objetivo) {

        if (objetivo == null) {
            return null;
        }

        return switch (objetivo) {
            case BAJAR_PESO -> -15.0;
            case MANTENER_PESO -> 0.0;
            case GANAR_PESO -> 10.0;
        };
    }
}