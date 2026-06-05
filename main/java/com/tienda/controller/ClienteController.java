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

import com.tienda.model.entity.Cliente;
import com.tienda.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

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

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@Valid @RequestBody Cliente cliente) {
        Cliente saved = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        return clienteService.findById(id)
            .map(existing -> {
                cliente.setId(id);
                return ResponseEntity.ok(clienteService.save(cliente));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        return clienteService.findById(id)
            .map(existing -> {
                clienteService.deleteById(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public List<Cliente> buscarPorNombre(@RequestParam(required = false, defaultValue = "") String nombre) {
        if (nombre.isBlank()) {
            return clienteService.findAll();
        }
        return clienteService.findByNombreContaining(nombre);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> getByEmail(@PathVariable String email) {
        return clienteService.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/contar-pedidos")
    public ResponseEntity<Long> contarPedidos(@PathVariable Long id) {
        return clienteService.findById(id)
            .map(cliente -> ResponseEntity.ok(clienteService.contarPedidosPorCliente(id)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/con-minimo-pedidos")
    public List<Cliente> getClientesConMinimoPedidos(@RequestParam(defaultValue = "1") Long minimo) {
        return clienteService.findClientesConMinimoPedidos(minimo);
    }

    @GetMapping("/por-videojuego/{videojuegoId}")
    public List<Cliente> getClientesByVideojuego(@PathVariable Long videojuegoId) {
        return clienteService.findClientesByVideojuegoId(videojuegoId);
    }
}
