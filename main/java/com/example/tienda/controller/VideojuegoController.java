package com.example.tienda.controller;

import com.example.tienda.model.entity.Videojuego;
import com.example.tienda.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller de Videojuegos
@RestController
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoController {
    
    private final VideojuegoService videojuegoService;
    
    public VideojuegoController(VideojuegoService videojuegoService) {
        this.videojuegoService = videojuegoService;
    }
    
    // CRUD
    
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
    public ResponseEntity<Videojuego> createVideojuego(@RequestBody Videojuego videojuego) {
        if (videojuegoService.existeTitulo(videojuego.getTitulo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Videojuego saved = videojuegoService.save(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> updateVideojuego(@PathVariable Long id, @RequestBody Videojuego videojuego) {
        if (!videojuegoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        videojuego.setId(id);
        return ResponseEntity.ok(videojuegoService.save(videojuego));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        if (!videojuegoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        videojuegoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Busquedas
    
    @GetMapping("/buscar")
    public List<Videojuego> buscarPorTitulo(@RequestParam String titulo) {
        return videojuegoService.findByTituloContaining(titulo);
    }
    
    @GetMapping("/genero/{genero}")
    public List<Videojuego> getByGenero(@PathVariable String genero) {
        return videojuegoService.findByGenero(genero);
    }
    
    @GetMapping("/plataforma/{plataforma}")
    public List<Videojuego> getByPlataforma(@PathVariable String plataforma) {
        return videojuegoService.findByPlataforma(plataforma);
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
    
    // Consultas
    
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
        if (!videojuegoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videojuegoService.contarPedidosPorVideojuego(id));
    }
}
