package com.tienda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.model.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    List<Cliente> findByEmailContainingIgnoreCase(String dominio);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.cliente.id = :clienteId")
    Long contarPedidosPorCliente(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Cliente c JOIN c.pedidos p GROUP BY c HAVING COUNT(p) >= :minPedidos")
    List<Cliente> findClientesConMinimoPedidos(@Param("minPedidos") Long minPedidos);

    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.pedidos p JOIN p.videojuegos v WHERE v.id = :videojuegoId")
    List<Cliente> findClientesByVideojuegoId(@Param("videojuegoId") Long videojuegoId);
}
