package com.cjguedes.tiendavideojuegos.service;

import com.cjguedes.tiendavideojuegos.exception.RecursoNoEncontradoException;
import com.cjguedes.tiendavideojuegos.model.Plataforma;
import com.cjguedes.tiendavideojuegos.model.Videojuego;
import com.cjguedes.tiendavideojuegos.repository.PlataformaRepository;
import com.cjguedes.tiendavideojuegos.repository.VideojuegoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Capa de servicio para Videojuego.
 * Orquesta la lógica de negocio: validaciones, asignación de relaciones y acceso a datos.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@Service
@RequiredArgsConstructor
public class VideojuegoService {

    private final VideojuegoRepository videojuegoRepository;
    private final PlataformaRepository plataformaRepository;

    /** Devuelve todos los videojuegos. */
    @Transactional(readOnly = true)
    public List<Videojuego> obtenerTodos() {
        return videojuegoRepository.findAll();
    }

    /**
     * Busca un videojuego por ID.
     * Devuelve Optional — el Controller gestiona el caso no encontrado.
     */
    @Transactional(readOnly = true)
    public Optional<Videojuego> obtenerPorId(Long id) {
        return videojuegoRepository.findById(id);
    }

    /**
     * Búsqueda con filtros opcionales.
     * Si los parámetros son null o vacíos, no se aplican como filtro.
     */
    @Transactional(readOnly = true)
    public List<Videojuego> buscar(String titulo, String genero) {
        String tituloFiltro = (titulo != null && !titulo.isBlank()) ? titulo : null;
        String generoFiltro = (genero != null && !genero.isBlank()) ? genero : null;
        return videojuegoRepository.buscarPorFiltros(tituloFiltro, generoFiltro);
    }

    /** Devuelve todos los videojuegos de una plataforma. */
    @Transactional(readOnly = true)
    public List<Videojuego> obtenerPorPlataforma(Long plataformaId) {
        if (!plataformaRepository.existsById(plataformaId)) {
            throw new RecursoNoEncontradoException("Plataforma no encontrada con id: " + plataformaId);
        }
        return videojuegoRepository.findByPlataformaId(plataformaId);
    }

    /** Devuelve videojuegos dentro de un rango de precio. */
    @Transactional(readOnly = true)
    public List<Videojuego> obtenerPorRangoPrecio(BigDecimal min, BigDecimal max) {
        return videojuegoRepository.findByRangoPrecio(min, max);
    }

    /** Cuenta los videojuegos de una plataforma (JPQL @Query). */
    @Transactional(readOnly = true)
    public Long contarPorPlataforma(Long plataformaId) {
        return videojuegoRepository.contarPorPlataforma(plataformaId);
    }

    /**
     * Crea un nuevo videojuego.
     * Si se proporciona plataformaId en el body, asigna la relación.
     */
    @Transactional
    public Videojuego crear(Videojuego videojuego, Long plataformaId) {
        if (plataformaId != null) {
            Plataforma plataforma = plataformaRepository.findById(plataformaId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Plataforma no encontrada con id: " + plataformaId));
            videojuego.setPlataforma(plataforma);
        }
        return videojuegoRepository.save(videojuego);
    }

    /**
     * Actualiza un videojuego existente.
     * Permite cambiar también la plataforma si se envía plataformaId.
     */
    @Transactional
    public Videojuego actualizar(Long id, Videojuego datos, Long plataformaId) {
        Videojuego existente = videojuegoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Videojuego no encontrado con id: " + id));

        existente.setTitulo(datos.getTitulo());
        existente.setGenero(datos.getGenero());
        existente.setEstudioDesarrollador(datos.getEstudioDesarrollador());
        existente.setAnioLanzamiento(datos.getAnioLanzamiento());
        existente.setPrecio(datos.getPrecio());
        existente.setDisponible(datos.getDisponible());
        existente.setDescripcion(datos.getDescripcion());

        if (plataformaId != null) {
            Plataforma plataforma = plataformaRepository.findById(plataformaId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Plataforma no encontrada con id: " + plataformaId));
            existente.setPlataforma(plataforma);
        }

        return videojuegoRepository.save(existente);
    }

    /** Elimina un videojuego por ID. */
    @Transactional
    public void eliminar(Long id) {
        if (!videojuegoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Videojuego no encontrado con id: " + id);
        }
        videojuegoRepository.deleteById(id);
    }
}
