package com.tienda.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

import com.tienda.model.entity.Pedido;
import com.tienda.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Long id) {
        return pedidoService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@Valid @RequestBody Pedido pedido) {
        Pedido saved = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        return pedidoService.findById(id)
            .map(existing -> {
                pedido.setId(id);
                return ResponseEntity.ok(pedidoService.save(pedido));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        return pedidoService.findById(id)
            .map(existing -> {
                pedidoService.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> getByCliente(@PathVariable Long clienteId) {
        return pedidoService.findByClienteId(clienteId);
    }

    @GetMapping("/estado/{estado}")
    public List<Pedido> getByEstado(@PathVariable String estado) {
        return pedidoService.findByEstado(estado);
    }

    @GetMapping("/buscar")
    public List<Pedido> buscar(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDate inicio,
            @RequestParam(required = false) LocalDate fin,
            @RequestParam(required = false, defaultValue = "fecha") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order) {
        return pedidoService.buscar(clienteId, estado, inicio, fin, sortBy, order);
    }

    @GetMapping("/videojuego/{videojuegoId}")
    public List<Pedido> getByVideojuego(@PathVariable Long videojuegoId) {
        return pedidoService.findByVideojuegoId(videojuegoId);
    }

    @GetMapping("/total-superior-promedio")
    public List<Pedido> getConTotalSuperiorAlPromedio() {
        return pedidoService.findPedidosConTotalSuperiorAlPromedio();
    }

    @GetMapping("/con-minimo-videojuegos")
    public List<Pedido> getPedidosConMinimoVideojuegos(@RequestParam(defaultValue = "1") Long minimo) {
        return pedidoService.findPedidosConMinimoVideojuegos(minimo);
    }

    @GetMapping("/cliente/{clienteId}/resumen")
    public ResponseEntity<Map<String, Object>> getResumenCliente(
            @PathVariable Long clienteId,
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) {
        Long pedidos = pedidoService.contarPedidosPorClienteYPeriodo(clienteId, inicio, fin);
        Double total = pedidoService.calcularTotalGastadoPorCliente(clienteId, inicio, fin);
        return ResponseEntity.ok(Map.of("pedidos", pedidos, "total", total));
    }

    @PostMapping("/{pedidoId}/videojuegos/{videojuegoId}")
    public ResponseEntity<Pedido> agregarVideojuego(
            @PathVariable Long pedidoId,
            @PathVariable Long videojuegoId) {
        return pedidoService.agregarVideojuego(pedidoId, videojuegoId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{pedidoId}/videojuegos/{videojuegoId}")
    public ResponseEntity<Pedido> quitarVideojuego(
            @PathVariable Long pedidoId,
            @PathVariable Long videojuegoId) {
        return pedidoService.quitarVideojuego(pedidoId, videojuegoId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
