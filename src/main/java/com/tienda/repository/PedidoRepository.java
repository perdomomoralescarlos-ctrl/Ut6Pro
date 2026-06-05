package com.tienda.repository;

import com.tienda.model.entity.Pedido;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Repository de Pedidos
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Metodos basicos
    
    // Buscar pedidos por cliente (usa la FK cliente_id)
    List<Pedido> findByClienteId(Long clienteId);
    
    // Buscar pedidos por cliente ordenados por fecha descendente
    List<Pedido> findByClienteId(Long clienteId, Sort sort);
    
    // Busquedas
    
    // Buscar pedidos por estado
    List<Pedido> findByEstadoIgnoreCase(String estado);
    
    // Buscar pedidos por rango de fechas
    List<Pedido> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar pedidos por estado y rango de fechas (Módulo B compuesto)
    
    List<Pedido> findByEstadoIgnoreCaseAndFechaBetween(String estado, LocalDate inicio, LocalDate fin);
    
    // Consultas JPQL
    
    // Buscar pedidos que tienen un videojuego
    @Query("SELECT p FROM Pedido p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    List<Pedido> findByVideojuegoId(@Param("videojuegoId") Long videojuegoId);
    
    // Contar pedidos de un cliente entre fechas
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fecha BETWEEN :inicio AND :fin")
    Long contarPedidosPorClienteYPeriodo(@Param("clienteId") Long clienteId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);
    
    // Sumar lo gastado por un cliente
    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fecha BETWEEN :inicio AND :fin")
    Double calcularTotalGastadoPorCliente(@Param("clienteId") Long clienteId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);
    
    // Pedidos con total mayor al promedio
    @Query("SELECT p FROM Pedido p WHERE p.total > (SELECT AVG(p2.total) FROM Pedido p2)")
    List<Pedido> findPedidosConTotalSuperiorAlPromedio();
    
    // SQL nativo pedidos con minimo de juegos
    @Query(value = "SELECT p.* FROM pedidos p " +
           "JOIN pedidos_videojuegos pv ON p.id = pv.pedido_id " +
           "GROUP BY p.id HAVING COUNT(pv.videojuego_id) >= :minVideojuegos",
           nativeQuery = true)
    List<Pedido> findPedidosConMinimoVideojuegosSQL(@Param("minVideojuegos") Long minVideojuegos);
}
