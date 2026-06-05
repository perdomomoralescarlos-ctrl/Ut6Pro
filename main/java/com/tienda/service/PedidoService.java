package com.tienda.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tienda.model.entity.Pedido;
import com.tienda.model.entity.Videojuego;
import com.tienda.repository.PedidoRepository;
import com.tienda.repository.VideojuegoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final VideojuegoRepository videojuegoRepository;

    public PedidoService(PedidoRepository pedidoRepository, VideojuegoRepository videojuegoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.videojuegoRepository = videojuegoRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findByEstado(String estado) {
        return pedidoRepository.findByEstadoIgnoreCase(estado);
    }

    public List<Pedido> buscar(Long clienteId, String estado, LocalDate inicio, LocalDate fin, String sortBy, String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        if (clienteId != null) {
            return pedidoRepository.findByClienteId(clienteId, sort);
        }
        if (estado != null && inicio != null && fin != null) {
            return pedidoRepository.findByEstadoIgnoreCaseAndFechaBetween(estado, inicio, fin);
        }
        if (estado != null) {
            return pedidoRepository.findByEstadoIgnoreCase(estado);
        }
        if (inicio != null && fin != null) {
            return pedidoRepository.findByFechaBetween(inicio, fin);
        }
        return pedidoRepository.findAll(sort);
    }

    public List<Pedido> findByVideojuegoId(Long videojuegoId) {
        return pedidoRepository.findByVideojuegoId(videojuegoId);
    }

    public Long contarPedidosPorClienteYPeriodo(Long clienteId, LocalDate inicio, LocalDate fin) {
        return pedidoRepository.contarPedidosPorClienteYPeriodo(clienteId, inicio, fin);
    }

    public Double calcularTotalGastadoPorCliente(Long clienteId, LocalDate inicio, LocalDate fin) {
        return pedidoRepository.calcularTotalGastadoPorCliente(clienteId, inicio, fin);
    }

    public List<Pedido> findPedidosConTotalSuperiorAlPromedio() {
        return pedidoRepository.findPedidosConTotalSuperiorAlPromedio();
    }

    public List<Pedido> findPedidosConMinimoVideojuegos(Long minVideojuegos) {
        return pedidoRepository.findPedidosConMinimoVideojuegos(minVideojuegos);
    }

    @Transactional
    public Optional<Pedido> agregarVideojuego(Long pedidoId, Long videojuegoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(videojuegoId);

        if (pedidoOpt.isPresent() && videojuegoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.getVideojuegos().add(videojuegoOpt.get());
            return Optional.of(pedidoRepository.save(pedido));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Pedido> quitarVideojuego(Long pedidoId, Long videojuegoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);
        Optional<Videojuego> videojuegoOpt = videojuegoRepository.findById(videojuegoId);

        if (pedidoOpt.isPresent() && videojuegoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.getVideojuegos().remove(videojuegoOpt.get());
            return Optional.of(pedidoRepository.save(pedido));
        }
        return Optional.empty();
    }
}
