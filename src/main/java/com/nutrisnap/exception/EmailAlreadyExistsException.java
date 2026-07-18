package com.nutrisnap.exception;

/**
 * Se lanza cuando un usuario intenta registrarse
 * con un correo ya existente.
 */
public class EmailAlreadyExistsException extends ApiException {

    public EmailAlreadyExistsException() {
        super("El correo electrónico ya está registrado.");
    }

}