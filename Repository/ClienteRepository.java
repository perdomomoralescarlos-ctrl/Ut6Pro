package com.example.tienda.repository;

import com.example.tienda.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Cliente Módulo B y C
 * Extiende JpaRepository para operaciones CRUD básicas
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Método derivado: buscar por email exacto
    Optional<Cliente> findByEmail(String email);
    
    // Módulo B: Método derivado con búsqueda parcial
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    // Módulo B: Método derivado con filtro por email
    List<Cliente> findByEmailContainingIgnoreCase(String dominio);
    
    /**
     * MÓDULO C: @Query JPQL
     * Contar cuántos pedidos tiene un cliente específico
     * 
     * "SELECT COUNT(p) FROM Pedido p" cuenta objetos Pedido, no filas de tabla
     * p.cliente.id accede a propiedades de objetos, no a columnas
     */
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId")
    Long contarPedidosPorCliente(@Param("clienteId") Long clienteId);
    
    /**
     * MÓDULO C: @Query JPQL más compleja
     * Buscar clientes que tengan al menos X pedidos
     * Usa JOIN explícito y GROUP BY con HAVING
     * 
     * JOIN c.pedidos navega por la relación @OneToMany
     * GROUP BY c agrupa por cliente
     * HAVING COUNT(p) >= :minPedidos filtra grupos
     */
    @Query("SELECT c FROM Cliente c JOIN c.pedidos p GROUP BY c HAVING COUNT(p) >= :minPedidos")
    List<Cliente> findClientesConMinimoPedidos(@Param("minPedidos") Long minPedidos);
    
    /**
     * MÓDULO C: @Query JPQL con subconsulta
     * Buscar clientes que han comprado un videojuego específico
     * Navega a través de múltiples relaciones: Cliente → Pedido → Videojuego
     */
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    List<Cliente> findClientesByVideojuegoId(@Param("videojuegoId") Long videojuegoId);
}
