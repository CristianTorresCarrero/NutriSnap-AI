package com.nutrisnap.service;

import com.nutrisnap.dto.CalculoNutricionalResponse;

public interface NutricionService {

    CalculoNutricionalResponse calcularNutricion(String email);

}