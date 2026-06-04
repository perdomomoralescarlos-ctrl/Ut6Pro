package com.cjguedes.tiendavideojuegos.controller;

import com.cjguedes.tiendavideojuegos.model.Videojuego;
import com.cjguedes.tiendavideojuegos.service.VideojuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST para el recurso Videojuego.
 * Ruta base: /api/v1/videojuegos
 *
 * @author Carlos Perdomo Morales
 */
@RestController
@RequestMapping("/api/v1/videojuegos")
@RequiredArgsConstructor
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    /**
     * GET /api/v1/videojuegos
     * Lista todos los videojuegos.
     * 200 OK + lista
     */
    @GetMapping
    public ResponseEntity<List<Videojuego>> listarTodos() {
        return ResponseEntity.ok(videojuegoService.obtenerTodos());
    }

    /**
     * GET /api/v1/videojuegos/{id}
     * Obtiene un videojuego por ID.
     * 200 OK si existe, 404 Not Found si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> obtenerPorId(@PathVariable Long id) {
        return videojuegoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/v1/videojuegos/buscar?titulo=&genero=
     *
     * Búsqueda por filtros opcionales (Módulo B).
     * Ejemplos:
     *   /buscar?titulo=zelda
     *   /buscar?genero=RPG
     *   /buscar?titulo=mario&genero=plataformas
     *   /buscar (sin parámetros → devuelve todos ordenados por título)
     *
     * required=false hace que el parámetro sea completamente opcional.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Videojuego>> buscar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero) {
        return ResponseEntity.ok(videojuegoService.buscar(titulo, genero));
    }

    /**
     * GET /api/v1/videojuegos/plataforma/{plataformaId}
     *
     * Devuelve todos los videojuegos de una plataforma concreta (Módulo A — usa la relación).
     * 200 OK + lista, 404 si la plataforma no existe.
     */
    @GetMapping("/plataforma/{plataformaId}")
    public ResponseEntity<List<Videojuego>> obtenerPorPlataforma(@PathVariable Long plataformaId) {
        return ResponseEntity.ok(videojuegoService.obtenerPorPlataforma(plataformaId));
    }

    /**
     * GET /api/v1/videojuegos/plataforma/{plataformaId}/count
     *
     * Cuenta cuántos videojuegos hay en una plataforma (usa @Query JPQL).
     */
    @GetMapping("/plataforma/{plataformaId}/count")
    public ResponseEntity<Long> contarPorPlataforma(@PathVariable Long plataformaId) {
        return ResponseEntity.ok(videojuegoService.contarPorPlataforma(plataformaId));
    }

    /**
     * GET /api/v1/videojuegos/precio?min=&max=
     *
     * Filtra videojuegos por rango de precio.
     * Ejemplo: /precio?min=10.00&max=60.00
     */
    @GetMapping("/precio")
    public ResponseEntity<List<Videojuego>> obtenerPorRangoPrecio(
            @RequestParam(defaultValue = "0.00") BigDecimal min,
            @RequestParam(defaultValue = "9999.99") BigDecimal max) {
        return ResponseEntity.ok(videojuegoService.obtenerPorRangoPrecio(min, max));
    }

    /**
     * POST /api/v1/videojuegos?plataformaId=
     *
     * Crea un videojuego. Si se pasa plataformaId como query param, lo asocia.
     * 201 Created + URI del nuevo recurso.
     */
    @PostMapping
    public ResponseEntity<Videojuego> crear(
            @Valid @RequestBody Videojuego videojuego,
            @RequestParam(required = false) Long plataformaId) {
        Videojuego nuevo = videojuegoService.crear(videojuego, plataformaId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId())
                .toUri();
        return ResponseEntity.created(location).body(nuevo);
    }

    /**
     * PUT /api/v1/videojuegos/{id}?plataformaId=
     *
     * Actualiza un videojuego. Opcionalmente cambia la plataforma.
     * 200 OK si existe, 404 si no.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Videojuego videojuego,
            @RequestParam(required = false) Long plataformaId) {
        return ResponseEntity.ok(videojuegoService.actualizar(id, videojuego, plataformaId));
    }

    /**
     * DELETE /api/v1/videojuegos/{id}
     * Elimina un videojuego.
     * 204 No Content si se elimina, 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        videojuegoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
