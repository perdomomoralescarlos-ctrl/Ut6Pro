package com.example.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.tienda.model.entity.Pedido;
import com.example.tienda.model.entity.Videojuego;
import com.example.tienda.repository.PedidoRepository;
import com.example.tienda.repository.VideojuegoRepository;

/**
 * Servicio de Pedido
 * Gestiona las operaciones de pedidos
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final VideojuegoRepository videojuegoRepository;

    public PedidoService(PedidoRepository pedidoRepository, VideojuegoRepository videojuegoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.videojuegoRepository = videojuegoRepository;
    }

    // Obtener todos los pedidos
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    // Buscar pedido por ID
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    // Guardar pedido
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Eliminar pedido
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    // Buscar pedidos por cliente
    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // Buscar pedidos por estado
    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstadoIgnoreCase(estado);
    }

    // Agregar videojuego a pedido
    public Pedido agregarVideojuego(Long pedidoId, Long videojuegoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(videojuegoId);

        if (pedidoOpt.isPresent() && videojuegoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.getVideojuegos().add(videojuegoOpt.get());
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    // Quitar videojuego de pedido
    public Pedido quitarVideojuego(Long pedidoId, Long videojuegoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(videojuegoId);

        if (pedidoOpt.isPresent() && videojuegoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.getVideojuegos().remove(videojuegoOpt.get());
            return pedidoRepository.save(pedido);
        }
        return null;
    }
}
