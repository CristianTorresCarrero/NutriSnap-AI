package com.nutrisnap.exception;

import com.nutrisnap.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;

/**
 * -------------------------------------------------------
 * Maneja todas las excepciones de la aplicación.
 * -------------------------------------------------------
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException ex){

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("El cuerpo de la solicitud contiene un valor inválido o tiene un formato incorrecto.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {

        ex.printStackTrace();

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Ha ocurrido un error interno en el servidor.")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Los datos enviados no son válidos.");

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(mensaje)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        String mensaje;

        if (ex.getRequiredType() != null &&
                ex.getRequiredType().isEnum()) {

            mensaje = "El valor '" + ex.getValue()
                    + "' no es válido para " + ex.getName() + ".";

        } else {

            mensaje = "El valor enviado para '"
                    + ex.getName()
                    + "' no tiene el formato correcto.";
        }

        ApiError error = ApiError.builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST.value())
                .message(mensaje)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .badRequest()
                .body(error);
    }

}
