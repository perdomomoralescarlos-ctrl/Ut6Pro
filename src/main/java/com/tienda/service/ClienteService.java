package com.example.tienda.service;

import com.example.tienda.model.entity.Cliente;
import com.example.tienda.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service de Clientes
@Service
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    // CRUD
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
    
    // Metodos de busqueda
    
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public List<Cliente> findByNombreContaining(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Metodos @Query
    
    public Long contarPedidosPorCliente(Long clienteId) {
        return clienteRepository.contarPedidosPorCliente(clienteId);
    }
    
    public List<Cliente> findClientesConMinimoPedidos(Long minPedidos) {
        return clienteRepository.findClientesConMinimoPedidos(minPedidos);
    }
    
    public List<Cliente> findClientesByVideojuegoId(Long videojuegoId) {
        return clienteRepository.findClientesByVideojuegoId(videojuegoId);
    }
    
    // Otros metodos
    
    public boolean existeEmail(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }
}
