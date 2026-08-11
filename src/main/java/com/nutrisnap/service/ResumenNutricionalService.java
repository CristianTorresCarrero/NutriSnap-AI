package com.nutrisnap.service;

import com.nutrisnap.dto.ResumenNutricionalDiarioResponse;

public interface ResumenNutricionalService {

    ResumenNutricionalDiarioResponse obtenerResumenDiario(
            String email
    );

}
