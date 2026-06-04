package com.cjguedes.tiendavideojuegos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa una plataforma de videojuegos (PS5, Xbox, PC...).
 * Lado "uno" de la relación @OneToMany con Videojuego.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@Entity
@Table(name = "plataformas")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "videojuegos") // Excluido para evitar recursión infinita en toString
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la plataforma no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Size(max = 50, message = "El fabricante no puede superar los 50 caracteres")
    @Column(length = 50)
    private String fabricante;

    @Column(name = "anio_lanzamiento")
    private Integer anioLanzamiento;

    @Column(length = 255)
    private String descripcion;

    /**
     * Relación inversa: una plataforma puede tener muchos videojuegos.
     * @JsonIgnore evita la serialización circular al devolver una Plataforma
     * (si se serializa Plataforma → lista de Videojuegos → cada Videojuego tiene Plataforma → bucle).
     */
    @OneToMany(mappedBy = "plataforma", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Videojuego> videojuegos = new ArrayList<>();
}
