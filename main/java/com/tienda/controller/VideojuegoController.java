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

import com.tienda.model.entity.Videojuego;
import com.tienda.service.VideojuegoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoController {

    private final VideojuegoService videojuegoService;

    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }

    @GetMapping
    public List<Videojuego> getAllVideojuegos() {
        return videojuegoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuegoById(@PathVariable Long id) {
        return videojuegoService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Videojuego> createVideojuego(@Valid @RequestBody Videojuego videojuego) {
        Videojuego saved = videojuegoService.save(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> updateVideojuego(@PathVariable Long id, @Valid @RequestBody Videojuego videojuego) {
        return videojuegoService.findById(id)
            .map(existing -> {
                videojuego.setId(id);
                return ResponseEntity.ok(videojuegoService.save(videojuego));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        return videojuegoService.findById(id)
            .map(existing -> {
                videojuegoService.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Videojuego> buscar(
            @RequestParam(required = false, defaultValue = "") String titulo,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return videojuegoService.buscar(titulo, sortBy, order);
    }

    @GetMapping("/genero/{genero}")
    public List<Videojuego> getByGenero(@PathVariable String genero) {
        return videojuegoService.findByGenero(genero);
    }

    @GetMapping("/plataforma/{plataforma}")
    public List<Videojuego> getByPlataforma(@PathVariable String plataforma) {
        return videojuegoService.findByPlataforma(plataforma);
    }

    @GetMapping("/plataforma/id/{plataformaId}")
    public List<Videojuego> getByPlataformaId(@PathVariable Long plataformaId) {
        return videojuegoService.findByPlataformaId(plataformaId);
    }

    @GetMapping("/filtro")
    public List<Videojuego> getByGeneroAndPlataforma(
            @RequestParam String genero,
            @RequestParam String plataforma) {
        return videojuegoService.findByGeneroAndPlataforma(genero, plataforma);
    }

    @GetMapping("/precio-maximo")
    public List<Videojuego> getByPrecioMaximo(@RequestParam Double precio) {
        return videojuegoService.findByPrecioMaximo(precio);
    }

    @GetMapping("/en-stock")
    public List<Videojuego> getEnStock() {
        return videojuegoService.findEnStock();
    }

    @GetMapping("/sin-pedidos")
    public List<Videojuego> getVideojuegosSinPedidos() {
        return videojuegoService.findVideojuegosSinPedidos();
    }

    @GetMapping("/mas-vendidos")
    public List<Videojuego> getMasVendidos() {
        return videojuegoService.findAllOrderByNumPedidosDesc();
    }

    @GetMapping("/{id}/contar-pedidos")
    public ResponseEntity<Long> contarPedidos(@PathVariable Long id) {
        return videojuegoService.findById(id)
            .map(videojuego -> ResponseEntity.ok(videojuegoService.contarPedidosPorVideojuego(id)))
            .orElse(ResponseEntity.notFound().build());
    }
}
