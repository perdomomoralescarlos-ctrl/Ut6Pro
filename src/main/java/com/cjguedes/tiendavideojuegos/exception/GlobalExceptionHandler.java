package com.cjguedes.tiendavideojuegos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones (Módulo D — calidad).
 *
 * @RestControllerAdvice intercepta todas las excepciones lanzadas desde cualquier
 * controlador y devuelve respuestas de error consistentes en JSON en lugar del
 * error HTML por defecto de Spring Boot.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura RecursoNoEncontradoException → 404 Not Found.
     * Devuelve JSON con timestamp, estado, error y mensaje.
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Captura MethodArgumentNotValidException → 400 Bad Request.
     * Se lanza cuando @Valid detecta que el body no cumple las restricciones
     * (@NotBlank, @Size, @DecimalMin, etc.).
     * Devuelve un mapa campo → mensaje de error para cada campo inválido.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> erroresCampos = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String campo = ((FieldError) err).getField();
            String mensaje = err.getDefaultMessage();
            erroresCampos.put(campo, mensaje);
        });

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Validation Failed");
        respuesta.put("campos", erroresCampos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Captura cualquier otra excepción no controlada → 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("mensaje", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
