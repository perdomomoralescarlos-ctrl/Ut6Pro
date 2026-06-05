package com.tienda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.model.entity.Plataforma;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    Optional<Plataforma> findByNombreIgnoreCase(String nombre);

    List<Plataforma> findByNombreContainingIgnoreCase(String nombre);

    List<Plataforma> findByFabricanteContainingIgnoreCase(String fabricante);

    @Query("SELECT COUNT(v) FROM Videojuego v WHERE v.plataforma.id = :plataformaId")
    Long contarVideojuegosPorPlataforma(@Param("plataformaId") Long plataformaId);
}
