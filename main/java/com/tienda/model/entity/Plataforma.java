package com.tienda.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "plataformas")
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    @NotBlank(message = "El nombre de la plataforma es obligatorio")
    @Size(max = 80, message = "El nombre de la plataforma no puede superar 80 caracteres")
    private String nombre;

    @Column(nullable = false, length = 80)
    @NotBlank(message = "El fabricante es obligatorio")
    @Size(max = 80, message = "El fabricante no puede superar 80 caracteres")
    private String fabricante;

    @Column(length = 40)
    @Size(max = 40, message = "La generacion no puede superar 40 caracteres")
    private String generacion;

    @OneToMany(mappedBy = "plataforma")
    @JsonIgnore
    private List<Videojuego> videojuegos = new ArrayList<>();

    public Plataforma() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getGeneracion() {
        return generacion;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }
}
