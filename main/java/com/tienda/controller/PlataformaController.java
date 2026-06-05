package com.tienda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.model.entity.Plataforma;
import com.tienda.service.PlataformaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/plataformas")
public class PlataformaController {

    private final PlataformaService plataformaService;

    public PlataformaController(PlataformaService plataformaService) {
        this.plataformaService = plataformaService;
    }

    @GetMapping
    public List<Plataforma> getAllPlataformas() {
        return plataformaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plataforma> getPlataformaById(@PathVariable Long id) {
        return plataformaService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plataforma> createPlataforma(@Valid @RequestBody Plataforma plataforma) {
        Plataforma saved = plataformaService.save(plataforma);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> updatePlataforma(@PathVariable Long id, @Valid @RequestBody Plataforma plataforma) {
        return plataformaService.findById(id)
            .map(existing -> {
                plataforma.setId(id);
                return ResponseEntity.ok(plataformaService.save(plataforma));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlataforma(@PathVariable Long id) {
        return plataformaService.findById(id)
            .map(existing -> {
                plataformaService.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Plataforma> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String fabricante) {
        return plataformaService.buscar(nombre, fabricante);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Plataforma> getByNombre(@PathVariable String nombre) {
        return plataformaService.findByNombre(nombre)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/contar-videojuegos")
    public ResponseEntity<Long> contarVideojuegos(@PathVariable Long id) {
        return plataformaService.findById(id)
            .map(plataforma -> ResponseEntity.ok(plataformaService.contarVideojuegosPorPlataforma(id)))
            .orElse(ResponseEntity.notFound().build());
    }
}
