package com.nutrisnap.exception;

/**
 * Se lanza cuando un recurso no existe.
 */
public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(String recurso) {
        super(recurso + " no encontrado.");
    }

}
