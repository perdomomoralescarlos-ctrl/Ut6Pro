package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tienda.model.entity.Pedido;
import com.tienda.model.entity.Videojuego;
import com.tienda.repository.PedidoRepository;
import com.tienda.repository.VideojuegoRepository;

// Service de Pedidos
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final VideojuegoRepository videojuegoRepository;

    public PedidoService(PedidoRepository pedidoRepository, VideojuegoRepository videojuegoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.videojuegoRepository = videojuegoRepository;
    }

    // Listar todos
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    // Buscar por id
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    // Guardar
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Eliminar
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    // Pedidos de un cliente
    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // Por estado
    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstadoIgnoreCase(estado);
    }

    // Añadir juego
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

    // Quitar juego
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
