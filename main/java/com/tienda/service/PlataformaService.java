package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tienda.model.entity.Plataforma;
import com.tienda.repository.PlataformaRepository;

@Service
public class PlataformaService {

    private final PlataformaRepository plataformaRepository;

    public PlataformaService(PlataformaRepository plataformaRepository) {
        this.plataformaRepository = plataformaRepository;
    }

    public List<Plataforma> findAll() {
        return plataformaRepository.findAll();
    }

    public Optional<Plataforma> findById(Long id) {
        return plataformaRepository.findById(id);
    }

    public Plataforma save(Plataforma plataforma) {
        return plataformaRepository.save(plataforma);
    }

    public void deleteById(Long id) {
        plataformaRepository.deleteById(id);
    }

    public Optional<Plataforma> findByNombre(String nombre) {
        return plataformaRepository.findByNombreIgnoreCase(nombre);
    }

    public List<Plataforma> buscar(String nombre, String fabricante) {
        if (nombre != null && !nombre.isBlank()) {
            return plataformaRepository.findByNombreContainingIgnoreCase(nombre);
        }
        if (fabricante != null && !fabricante.isBlank()) {
            return plataformaRepository.findByFabricanteContainingIgnoreCase(fabricante);
        }
        return plataformaRepository.findAll();
    }

    public Long contarVideojuegosPorPlataforma(Long plataformaId) {
        return plataformaRepository.contarVideojuegosPorPlataforma(plataformaId);
    }
}
