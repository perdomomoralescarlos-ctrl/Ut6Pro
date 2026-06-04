package com.cjguedes.tiendavideojuegos.controller;

import com.cjguedes.tiendavideojuegos.model.Plataforma;
import com.cjguedes.tiendavideojuegos.service.PlataformaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para el recurso Plataforma.
 * Ruta base: /api/v1/plataformas
 *
 * El Controller solo delega en el Service — nunca accede directamente al Repository.
 *
 * @author Carlos Perdomo Morales
 */
@RestController
@RequestMapping("/api/v1/plataformas")
@RequiredArgsConstructor
public class PlataformaController {

    private final PlataformaService plataformaService;

    /**
     * GET /api/v1/plataformas
     * Devuelve la lista completa de plataformas.
     * 200 OK + lista (vacía si no hay ninguna)
     */
    @GetMapping
    public ResponseEntity<List<Plataforma>> listarTodas() {
        return ResponseEntity.ok(plataformaService.obtenerTodas());
    }

    /**
     * GET /api/v1/plataformas/{id}
     * Busca una plataforma por ID.
     * 200 OK si existe, 404 Not Found si no.
     * Optional.map/orElse evita llamar a .get() sin comprobar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Plataforma> obtenerPorId(@PathVariable Long id) {
        return plataformaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/plataformas
     * Crea una nueva plataforma.
     * @Valid activa las anotaciones de validación del modelo.
     * 201 Created + URI del nuevo recurso en la cabecera Location.
     */
    @PostMapping
    public ResponseEntity<Plataforma> crear(@Valid @RequestBody Plataforma plataforma) {
        Plataforma nueva = plataformaService.crear(plataforma);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nueva.getId())
                .toUri();
        return ResponseEntity.created(location).body(nueva);
    }

    /**
     * PUT /api/v1/plataformas/{id}
     * Actualiza una plataforma existente.
     * 200 OK si se actualiza, 404 Not Found si no existe (lanzado desde el Service).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Plataforma plataforma) {
        return ResponseEntity.ok(plataformaService.actualizar(id, plataforma));
    }

    /**
     * DELETE /api/v1/plataformas/{id}
     * Elimina una plataforma.
     * 204 No Content si se elimina, 404 Not Found si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        plataformaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
