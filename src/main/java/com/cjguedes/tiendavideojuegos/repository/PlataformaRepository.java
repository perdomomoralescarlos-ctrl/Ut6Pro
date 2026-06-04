package com.cjguedes.tiendavideojuegos.repository;

import com.cjguedes.tiendavideojuegos.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para Plataforma.
 * Extiende JpaRepository para obtener CRUD completo + paginación/ordenación.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    /**
     * Método derivado: busca plataforma por nombre exacto (ignorando mayúsculas).
     * Spring Data JPA genera el SQL automáticamente a partir del nombre del método.
     */
    Optional<Plataforma> findByNombreIgnoreCase(String nombre);

    /**
     * Método derivado: comprueba si ya existe una plataforma con ese nombre.
     */
    boolean existsByNombreIgnoreCase(String nombre);
}
