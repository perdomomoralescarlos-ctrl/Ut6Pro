package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tienda.model.entity.Cliente;
import com.tienda.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

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

    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> findByNombreContaining(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Long contarPedidosPorCliente(Long clienteId) {
        return clienteRepository.contarPedidosPorCliente(clienteId);
    }

    public List<Cliente> findClientesConMinimoPedidos(Long minPedidos) {
        return clienteRepository.findClientesConMinimoPedidos(minPedidos);
    }

    public List<Cliente> findClientesByVideojuegoId(Long videojuegoId) {
        return clienteRepository.findClientesByVideojuegoId(videojuegoId);
    }
}
