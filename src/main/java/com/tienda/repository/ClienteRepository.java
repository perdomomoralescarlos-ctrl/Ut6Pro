package com.tienda.repository;

import com.tienda.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository de Clientes
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Buscar por email
    Optional<Cliente> findByEmail(String email);
    
    // Buscar por nombre
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar por dominio de email
    List<Cliente> findByEmailContainingIgnoreCase(String dominio);
    
    // Contar pedidos de un cliente
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId")
    Long contarPedidosPorCliente(@Param("clienteId") Long clienteId);
    
    // Clientes con minimo de pedidos
    @Query("SELECT c FROM Cliente c JOIN c.pedidos p GROUP BY c HAVING COUNT(p) >= :minPedidos")
    List<Cliente> findClientesConMinimoPedidos(@Param("minPedidos") Long minPedidos);
    
    // Clientes que compraron un juego
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    List<Cliente> findClientesByVideojuegoId(@Param("videojuegoId") Long videojuegoId);
}
