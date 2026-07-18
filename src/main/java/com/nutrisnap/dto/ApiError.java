package com.nutrisnap.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa una respuesta de error estándar.
 */

@Data
@Builder
public class ApiError {

    private boolean success;

    private int status;

    private String message;

    private LocalDateTime timestamp;
}
