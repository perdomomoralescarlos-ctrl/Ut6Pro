package com.cjguedes.tiendavideojuegos.repository;

import com.cjguedes.tiendavideojuegos.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio JPA para Videojuego.
 * Incluye métodos derivados (Spring Data los genera automáticamente)
 * y consultas JPQL personalizadas con @Query.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    // ─── Métodos derivados ────────────────────────────────────────────────────

    /**
     * Busca videojuegos cuyo título contenga el texto dado, sin importar mayúsculas.
     * SQL generado: WHERE UPPER(titulo) LIKE UPPER('%texto%')
     */
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca videojuegos por género (ignorando mayúsculas).
     */
    List<Videojuego> findByGeneroIgnoreCase(String genero);

    /**
     * Busca videojuegos disponibles o no disponibles según el parámetro.
     */
    List<Videojuego> findByDisponible(Boolean disponible);

    /**
     * Obtiene todos los videojuegos de una plataforma concreta.
     * Navega la relación: Videojuego.plataforma.id
     */
    List<Videojuego> findByPlataformaId(Long plataformaId);

    // ─── Consultas JPQL con @Query ────────────────────────────────────────────

    /**
     * Cuenta cuántos videojuegos hay en una plataforma.
     * Se usa @Query porque el método derivado equivalente sería ambiguo
     * y aquí se quiere devolver un Long escalar, no una lista.
     *
     * JPQL opera sobre entidades Java (Videojuego, Plataforma), no sobre tablas SQL.
     */
    @Query("SELECT COUNT(v) FROM Videojuego v WHERE v.plataforma.id = :plataformaId")
    Long contarPorPlataforma(@Param("plataformaId") Long plataformaId);

    /**
     * Busca videojuegos dentro de un rango de precio.
     * Un método derivado (findByPrecioBetween) funcionaría, pero con @Query
     * es más legible cuando el criterio se complica.
     */
    @Query("SELECT v FROM Videojuego v WHERE v.precio BETWEEN :min AND :max ORDER BY v.precio ASC")
    List<Videojuego> findByRangoPrecio(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    /**
     * Búsqueda combinada: filtra por título y/o género usando parámetros opcionales.
     * Cuando un parámetro es null, la condición se ignora (:param IS NULL OR ...).
     */
    @Query("""
            SELECT v FROM Videojuego v
            WHERE (:titulo IS NULL OR LOWER(v.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))
              AND (:genero IS NULL OR LOWER(v.genero) = LOWER(:genero))
            ORDER BY v.titulo ASC
            """)
    List<Videojuego> buscarPorFiltros(
            @Param("titulo") String titulo,
            @Param("genero") String genero
    );
}
