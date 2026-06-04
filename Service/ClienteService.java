package com.example.tienda.service;

import com.example.tienda.model.entity.Cliente;
import com.example.tienda.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de Cliente
 * Intermediario entre Controller y Repository
 * Uso Optional<Cliente> para manejar resultados que pueden no existir
 */
@Service
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    // CRUD Básico
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    /**
     * Uso correcto de Optional
     * Devuelve Optional<Cliente> para que el controller decida qué hacer si no existe
     */
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
    
    // Métodos derivados del Repository
    
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public List<Cliente> findByNombreContaining(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    // Métodos @Query del Repository
    
    /**
     * Contar pedidos de un cliente usando método @Query
     */
    public Long contarPedidosPorCliente(Long clienteId) {
        return clienteRepository.contarPedidosPorCliente(clienteId);
    }
    
    /**
     * Buscar clientes con mínimo de pedidos usando @Query con HAVING
     */
    public List<Cliente> findClientesConMinimoPedidos(Long minPedidos) {
        return clienteRepository.findClientesConMinimoPedidos(minPedidos);
    }
    
    /**
     * Buscar clientes que compraron un videojuego específico
     */
    public List<Cliente> findClientesByVideojuegoId(Long videojuegoId) {
        return clienteRepository.findClientesByVideojuegoId(videojuegoId);
    }
    
    // Lógica de negocio adicional
    
    /**
     * Verificar si un cliente existe antes de crearlo (evitar duplicados por email)
     */
    public boolean existeEmail(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }
}
