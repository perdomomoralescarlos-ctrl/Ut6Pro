package com.tienda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.model.entity.Videojuego;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    Optional<Videojuego> findByTituloIgnoreCase(String titulo);

    List<Videojuego> findByTituloContainingIgnoreCase(String titulo, Sort sort);

    List<Videojuego> findByGeneroIgnoreCase(String genero);

    List<Videojuego> findByPlataformaNombreIgnoreCase(String plataforma);

    List<Videojuego> findByGeneroIgnoreCaseAndPlataformaNombreIgnoreCase(String genero, String plataforma);

    List<Videojuego> findByPrecioLessThanEqual(Double precio);

    List<Videojuego> findByStockGreaterThan(Integer stock);

    List<Videojuego> findByPlataformaId(Long plataformaId);

    @Query("SELECT v FROM Videojuego v WHERE v.pedidos IS EMPTY")
    List<Videojuego> findVideojuegosSinPedidos();

    @Query("SELECT v FROM Videojuego v LEFT JOIN v.pedidos p GROUP BY v ORDER BY COUNT(p) DESC")
    List<Videojuego> findAllOrderByNumPedidosDesc();

    @Query("SELECT COUNT(p) FROM Pedido p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    Long contarPedidosPorVideojuego(@Param("videojuegoId") Long videojuegoId);
}
