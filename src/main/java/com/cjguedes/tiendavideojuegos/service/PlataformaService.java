package com.cjguedes.tiendavideojuegos.service;

import com.cjguedes.tiendavideojuegos.exception.RecursoNoEncontradoException;
import com.cjguedes.tiendavideojuegos.model.Plataforma;
import com.cjguedes.tiendavideojuegos.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio para Plataforma.
 * Contiene la lógica de negocio y actúa como intermediario entre
 * el Controller y el Repository.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@Service
@RequiredArgsConstructor
public class PlataformaService {

    private final PlataformaRepository plataformaRepository;

    /** Devuelve todas las plataformas. */
    @Transactional(readOnly = true)
    public List<Plataforma> obtenerTodas() {
        return plataformaRepository.findAll();
    }

    /**
     * Busca una plataforma por ID.
     * Devuelve Optional<Plataforma> — el Controller decide qué hacer si está vacío.
     */
    @Transactional(readOnly = true)
    public Optional<Plataforma> obtenerPorId(Long id) {
        return plataformaRepository.findById(id);
    }

    /** Crea una nueva plataforma. */
    @Transactional
    public Plataforma crear(Plataforma plataforma) {
        return plataformaRepository.save(plataforma);
    }

    /**
     * Actualiza una plataforma existente.
     * Lanza RecursoNoEncontradoException si el ID no existe.
     */
    @Transactional
    public Plataforma actualizar(Long id, Plataforma datos) {
        Plataforma existente = plataformaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plataforma no encontrada con id: " + id));

        existente.setNombre(datos.getNombre());
        existente.setFabricante(datos.getFabricante());
        existente.setAnioLanzamiento(datos.getAnioLanzamiento());
        existente.setDescripcion(datos.getDescripcion());

        return plataformaRepository.save(existente);
    }

    /**
     * Elimina una plataforma por ID.
     * Lanza RecursoNoEncontradoException si el ID no existe.
     */
    @Transactional
    public void eliminar(Long id) {
        if (!plataformaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Plataforma no encontrada con id: " + id);
        }
        plataformaRepository.deleteById(id);
    }
}
