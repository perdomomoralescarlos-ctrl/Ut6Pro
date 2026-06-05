package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tienda.model.entity.Videojuego;
import com.tienda.repository.VideojuegoRepository;

@Service
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;

    public VideojuegoService(VideojuegoRepository videojuegoRepository) {
        this.videojuegoRepository = videojuegoRepository;
    }

    public List<Videojuego> findAll() {
        return videojuegoRepository.findAll();
    }

    public Optional<Videojuego> findById(Long id) {
        return videojuegoRepository.findById(id);
    }

    public Videojuego save(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    public void deleteById(Long id) {
        videojuegoRepository.deleteById(id);
    }

    public List<Videojuego> buscar(String titulo, String sortBy, String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        if (titulo == null || titulo.isBlank()) {
            return videojuegoRepository.findAll(sort);
        }
        return videojuegoRepository.findByTituloContainingIgnoreCase(titulo, sort);
    }

    public List<Videojuego> findByGenero(String genero) {
        return videojuegoRepository.findByGeneroIgnoreCase(genero);
    }

    public List<Videojuego> findByPlataforma(String plataforma) {
        return videojuegoRepository.findByPlataformaNombreIgnoreCase(plataforma);
    }

    public List<Videojuego> findByPlataformaId(Long plataformaId) {
        return videojuegoRepository.findByPlataformaId(plataformaId);
    }

    public List<Videojuego> findByGeneroAndPlataforma(String genero, String plataforma) {
        return videojuegoRepository.findByGeneroIgnoreCaseAndPlataformaNombreIgnoreCase(genero, plataforma);
    }

    public List<Videojuego> findByPrecioMaximo(Double precio) {
        return videojuegoRepository.findByPrecioLessThanEqual(precio);
    }

    public List<Videojuego> findEnStock() {
        return videojuegoRepository.findByStockGreaterThan(0);
    }

    public List<Videojuego> findVideojuegosSinPedidos() {
        return videojuegoRepository.findVideojuegosSinPedidos();
    }

    public List<Videojuego> findAllOrderByNumPedidosDesc() {
        return videojuegoRepository.findAllOrderByNumPedidosDesc();
    }

    public Long contarPedidosPorVideojuego(Long videojuegoId) {
        return videojuegoRepository.contarPedidosPorVideojuego(videojuegoId);
    }
}
