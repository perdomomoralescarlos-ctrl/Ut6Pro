package com.example.tienda.controller;

import com.example.tienda.model.entity.Cliente;
import com.example.tienda.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Cliente Endpoints REST
 * Módulos A, C y D: CRUD, @Query y @Valid
 */
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    // CRUD Básico
    
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * MÓDULO D: @Valid para validación automática
     * Las validaciones definidas en la entidad se ejecutan automáticamente
     */
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) {
        // Verificar email duplicado
        if (clienteService.existeEmail(cliente.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Cliente saved = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        if (!clienteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id);
        return ResponseEntity.ok(clienteService.save(cliente));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (!clienteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // Métodos derivados (Módulo B)
    
    @GetMapping("/buscar")
    public List<Cliente> buscarPorNombre(@RequestParam String nombre) {
        return clienteService.findByNombreContaining(nombre);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> getByEmail(@PathVariable String email) {
        return clienteService.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Métodos @Query (Módulo C)
    
    @GetMapping("/{id}/contar-pedidos")
    public ResponseEntity<Long> contarPedidos(@PathVariable Long id) {
        if (!clienteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteService.contarPedidosPorCliente(id));
    }
    
    @GetMapping("/con-minimo-pedidos")
    public List<Cliente> getClientesConMinimoPedidos(@RequestParam Long minimo) {
        return clienteService.findClientesConMinimoPedidos(minimo);
    }
    
    @GetMapping("/por-videojuego/{videojuegoId}")
    public List<Cliente> getClientesByVideojuego(@PathVariable Long videojuegoId) {
        return clienteService.findClientesByVideojuegoId(videojuegoId);
    }
}
