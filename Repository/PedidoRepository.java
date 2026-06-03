package com.example.tienda.repository;

import com.example.tienda.model.entity.Pedido;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Pedido Módulo A, B y C
 * Incluye métodos derivados y consultas @Query JPQL
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // MÓDULO A: Relación @ManyToOne
    
    // Buscar pedidos por cliente (usa la FK cliente_id)
    List<Pedido> findByClienteId(Long clienteId);
    
    // Buscar pedidos por cliente ordenados por fecha descendente
    List<Pedido> findByClienteId(Long clienteId, Sort sort);
    
    // MÓDULO B: Métodos derivados con filtros
    
    // Buscar pedidos por estado
    List<Pedido> findByEstadoIgnoreCase(String estado);
    
    // Buscar pedidos por rango de fechas
    List<Pedido> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar pedidos por estado y rango de fechas (Módulo B compuesto)
    List<Pedido> findByEstadoIgnoreCaseAndFechaBetween(String estado, LocalDate inicio, LocalDate fin);
    
    // MÓDULO C: @Query JPQL
    
    /**
     * MÓDULO C: @Query JPQL con JOIN
     * Buscar pedidos que contienen un videojuego específico
     * Navega a través de la relación @ManyToMany
     * 
     *JPQL: SELECT p FROM Pedido p JOIN p.videojuegos v
     *SQL equivalente: SELECT p.* FROM pedidos p 
     *                    JOIN pedidos_videojuegos pv ON p.id = pv.pedido_id
     *                    WHERE pv.videojuego_id = :id
     */
    @Query("SELECT p FROM Pedido p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    List<Pedido> findByVideojuegoId(@Param("videojuegoId") Long videojuegoId);
    
    /**
     * MÓDULO C: @Query JPQL con COUNT
     * Contar pedidos de un cliente en un período específico
     * 
     *Esta consulta no se puede hacer con métodos derivados porque:
     *Necesita COUNT con condiciones múltiples
     *Métodos derivados solo devuelven List<T> o Optional<T>, no Long
     */
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fecha BETWEEN :inicio AND :fin")
    Long contarPedidosPorClienteYPeriodo(@Param("clienteId") Long clienteId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);
    
    /**
     * MÓDULO C: @Query JPQL con SUM
     * Calcular el total gastado por un cliente en un período
     * 
     * Demuestra que @Query puede hacer agregaciones complejas (SUM, AVG, etc.)
     * que los métodos derivados no pueden expresar
     */
    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Pedido p WHERE p.cliente.id = :clienteId AND p.fecha BETWEEN :inicio AND :fin")
    Double calcularTotalGastadoPorCliente(@Param("clienteId") Long clienteId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fin") LocalDate fin);
    
    /**
     * MÓDULO C: @Query JPQL con subconsulta
     * Buscar pedidos con total superior al promedio de todos los pedidos
     * @Query("SELECT p FROM Pedido p WHERE p.total > (SELECT AVG(p2.total) FROM Pedido p2)")
     * List<Pedido> findPedidosConTotalSuperiorAlPromedio();
     */
    /**
     * MÓDULO C: @Query nativo (SQL) Ejemplo de cuando se necesita SQL puro
     * Buscar pedidos con al menos X videojuegos usando SQL nativo
     * 
     * nativeQuery = true indica que es SQL, no JPQL
     * Útil cuando necesitamos funciones específicas de la base de datos
     */
    @Query(value = "SELECT p.* FROM pedidos p " +
           "JOIN pedidos_videojuegos pv ON p.id = pv.pedido_id " +
           "GROUP BY p.id HAVING COUNT(pv.videojuego_id) >= :minVideojuegos",
           nativeQuery = true)
    List<Pedido> findPedidosConMinimoVideojuegosSQL(@Param("minVideojuegos") Long minVideojuegos);
}