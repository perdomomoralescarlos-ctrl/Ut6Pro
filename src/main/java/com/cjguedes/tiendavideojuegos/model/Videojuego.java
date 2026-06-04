package com.cjguedes.tiendavideojuegos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Entidad JPA que representa un videojuego de la tienda.
 * Lado "muchos" de la relación @ManyToOne con Plataforma.
 *
 * @author Carlos Perdomo (cjguedes) · UT6
 */
@Entity
@Table(name = "videojuegos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    @Column(nullable = false, length = 150)
    private String titulo;

    @Size(max = 80, message = "El género no puede superar los 80 caracteres")
    @Column(length = 80)
    private String genero;

    @Size(max = 100, message = "El estudio no puede superar los 100 caracteres")
    @Column(name = "estudio_desarrollador", length = 100)
    private String estudioDesarrollador;

    @Column(name = "anio_lanzamiento")
    private Integer anioLanzamiento;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Digits(integer = 6, fraction = 2, message = "Formato de precio no válido")
    @Column(precision = 8, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(length = 500)
    private String descripcion;

    /**
     * FK hacia Plataforma.
     * @JoinColumn define el nombre de la columna FK en la tabla videojuegos.
     * La plataforma se serializa dentro del videojuego (no tiene @JsonIgnore aquí),
     * lo que permite obtener la info completa de la plataforma en un solo GET /videojuegos/{id}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plataforma_id", referencedColumnName = "id")
    private Plataforma plataforma;
}
