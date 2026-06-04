package com.cjguedes.tiendavideojuegos.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso en la base de datos.
 * El GlobalExceptionHandler la captura y devuelve un 404 con mensaje JSON.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
