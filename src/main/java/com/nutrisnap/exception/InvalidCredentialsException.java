package com.nutrisnap.exception;

/**
 * Se lanza cuando las credenciales son incorrectas.
 */
public class InvalidCredentialsException extends ApiException {

    public InvalidCredentialsException() {
        super("Correo o contraseña incorrectos.");
    }

}
