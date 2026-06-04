package com.tienda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.model.entity.Pedido;
import com.tienda.service.PedidoService;

// Controller de Pedidos
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Listar pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.findById(id);
        if (pedido.isPresent()) {
            return ResponseEntity.ok(pedido.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Nuevo pedido
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        Pedido saved = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Modificar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Optional<Pedido> existing = pedidoService.findById(id);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        pedido.setId(id);
        return ResponseEntity.ok(pedidoService.save(pedido));
    }

    // Borrar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        Optional<Pedido> existing = pedidoService.findById(id);
        if (!existing.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Ver pedidos de un cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> getByCliente(@PathVariable Long clienteId) {
        return pedidoService.findByClienteId(clienteId);
    }

    // Filtrar por estado
    @GetMapping("/estado/{estado}")
    public List<Pedido> getByEstado(@PathVariable String estado) {
        return pedidoService.findByEstado(estado);
    }

    // Añadir juego al pedido
    @PostMapping("/{pedidoId}/videojuegos/{videojuegoId}")
    public ResponseEntity<Pedido> agregarVideojuego(
            @PathVariable Long pedidoId,
            @PathVariable Long videojuegoId) {
        Pedido pedido = pedidoService.agregarVideojuego(pedidoId, videojuegoId);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }

    // Quitar juego del pedido
    @DeleteMapping("/{pedidoId}/videojuegos/{videojuegoId}")
    public ResponseEntity<Pedido> quitarVideojuego(
            @PathVariable Long pedidoId,
            @PathVariable Long videojuegoId) {
        Pedido pedido = pedidoService.quitarVideojuego(pedidoId, videojuegoId);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.notFound().build();
    }
}
