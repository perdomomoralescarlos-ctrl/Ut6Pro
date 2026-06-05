package com.tienda.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.model.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByClienteId(Long clienteId, Sort sort);

    List<Pedido> findByEstadoIgnoreCase(String estado);

    List<Pedido> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Pedido> findByEstadoIgnoreCaseAndFechaBetween(String estado, LocalDate inicio, LocalDate fin);

    @Query("SELECT p FROM Pedido p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    List<Pedido> findByVideojuegoId(@Param("videojuegoId") Long videojuegoId);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fecha BETWEEN :inicio AND :fin")
    Long contarPedidosPorClienteYPeriodo(@Param("clienteId") Long clienteId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);

    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fecha BETWEEN :inicio AND :fin")
    Double calcularTotalGastadoPorCliente(@Param("clienteId") Long clienteId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);

    @Query("SELECT p FROM Pedido p WHERE p.total > (SELECT AVG(p2.total) FROM Pedido p2)")
    List<Pedido> findPedidosConTotalSuperiorAlPromedio();

    @Query("SELECT p FROM Pedido p JOIN p.videojuegos v GROUP BY p HAVING COUNT(v) >= :minVideojuegos")
    List<Pedido> findPedidosConMinimoVideojuegos(@Param("minVideojuegos") Long minVideojuegos);
}
