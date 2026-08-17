package com.nutrisnap.service;

import com.nutrisnap.dto.DashboardNutricionalResponse;

public interface DashboardNutricionalService {

    DashboardNutricionalResponse obtenerDashboard(
            String email
    );
}