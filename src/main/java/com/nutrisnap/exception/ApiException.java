package com.nutrisnap.exception;

/**
 * -------------------------------------------------------
 * Excepción base para la aplicación NutriSnap AI.
 * -------------------------------------------------------
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

}
