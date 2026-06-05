package com.tienda.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tienda.model.entity.Cliente;
import com.tienda.model.entity.Pedido;
import com.tienda.model.entity.Plataforma;
import com.tienda.model.entity.Videojuego;
import com.tienda.repository.ClienteRepository;
import com.tienda.repository.PedidoRepository;
import com.tienda.repository.PlataformaRepository;
import com.tienda.repository.VideojuegoRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
            ClienteRepository clienteRepository,
            PedidoRepository pedidoRepository,
            PlataformaRepository plataformaRepository,
            VideojuegoRepository videojuegoRepository) {
        return args -> {
            Plataforma ps5 = new Plataforma();
            ps5.setNombre("PlayStation 5");
            ps5.setFabricante("Sony");
            ps5.setGeneracion("Novena");

            Plataforma switch2 = new Plataforma();
            switch2.setNombre("Nintendo Switch 2");
            switch2.setFabricante("Nintendo");
            switch2.setGeneracion("Novena");

            plataformaRepository.saveAll(List.of(ps5, switch2));

            Videojuego eldenRing = new Videojuego();
            eldenRing.setTitulo("Elden Ring");
            eldenRing.setGenero("RPG");
            eldenRing.setPrecio(59.99);
            eldenRing.setStock(12);
            eldenRing.setPegi(16);
            eldenRing.setActivo(true);
            eldenRing.setPlataforma(ps5);

            Videojuego zelda = new Videojuego();
            zelda.setTitulo("The Legend of Zelda");
            zelda.setGenero("Aventura");
            zelda.setPrecio(69.99);
            zelda.setStock(8);
            zelda.setPegi(12);
            zelda.setActivo(true);
            zelda.setPlataforma(switch2);

            videojuegoRepository.saveAll(List.of(eldenRing, zelda));

            Cliente cliente = new Cliente();
            cliente.setNombre("Carlos Perdomo");
            cliente.setEmail("carlos@example.com");
            cliente.setTelefono("600111222");
            cliente.setDireccion("Calle Principal 1");
            clienteRepository.save(cliente);

            Pedido pedido = new Pedido();
            pedido.setFecha(LocalDate.now());
            pedido.setEstado("pagado");
            pedido.setTotal(129.98);
            pedido.setObservaciones("Pedido inicial de prueba");
            pedido.setCliente(cliente);
            pedido.getVideojuegos().add(eldenRing);
            pedido.getVideojuegos().add(zelda);
            pedidoRepository.save(pedido);
        };
    }
}
