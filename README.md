# Tienda API

Proyecto de API REST desarrollada con Spring Boot para gestionar una tienda de videojuegos.

Incluye gestión de videojuegos, plataformas, clientes y pedidos con persistencia en base de datos mediante JPA/Hibernate.

Trabajo de la unidad UT6 realizado por Aythami Reyes y Carlos Perdomo.

## Estructura del proyecto

- **Controller**: Endpoints REST
- **Service**: Lógica de negocio  
- **Repository**: Acceso a datos
- **Model**: Entidades JPA

## Entidades principales

- **Videojuego**: juegos con título, género, precio, stock
- **Plataforma**: consolas (PlayStation, Xbox, Nintendo)
- **Cliente**: compradores con nombre, email, teléfono
- **Pedido**: compras que relacionan clientes con videojuegos

## Endpoints principales

Base URL: `http://localhost:8080/api/v1`

### Videojuegos
- `GET /videojuegos` Listar todos
- `GET /videojuegos/{id}` Obtener por ID
- `POST /videojuegos` Crear (requiere auth)
- `PUT /videojuegos/{id}` Actualizar (requiere auth)
- `DELETE /videojuegos/{id}` Eliminar (requiere auth)

### Plataformas
- `GET /plataformas` Listar todas
- `GET /plataformas/{id}` Obtener por ID
- `POST /plataformas` Crear (requiere auth)
- `PUT /plataformas/{id}` Actualizar (requiere auth)
- `DELETE /plataformas/{id}` Eliminar (requiere auth)

### Clientes
- `GET /clientes` Listar todos
- `GET /clientes/{id}` Obtener por ID
- `POST /clientes` Crear (requiere auth)
- `PUT /clientes/{id}` Actualizar (requiere auth)
- `DELETE /clientes/{id}` Eliminar (requiere auth)

### Pedidos
- `GET /pedidos` Listar todos
- `GET /pedidos/{id}` Obtener por ID
- `POST /pedidos` Crear (requiere auth)
- `PUT /pedidos/{id}` Actualizar (requiere auth)
- `DELETE /pedidos/{id}` Eliminar (requiere auth)

## Tecnologías usadas

- Spring Boot 3.2.5
- Spring Data JPA
- Spring Security (HTTP Basic Auth)
- H2 Database (base de datos en memoria)
- Maven

## Relaciones entre entidades

- Un **cliente** tiene muchos **pedidos** (@OneToMany)
- Un **pedido** tiene muchos **videojuegos** (@ManyToMany)
- Una **plataforma** tiene muchos **videojuegos** (@OneToMany)

## Cómo arrancar el proyecto

1. Abrir terminal en la carpeta del proyecto
2. Ejecutar:
```bash
mvn spring-boot:run
```
3. La API estará en `http://localhost:8080`

## Accesos

- **API**: `http://localhost:8080/api/v1`
- **Página web**: `http://localhost:8080/`
- **Consola H2**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:tiendadb`
  - Usuario: `usuario`
  - Contraseña: `1234`

## Seguridad

- **GET**: Público (sin login)
- **POST, PUT, DELETE**: Requieren autenticación HTTP Basic
- **Credenciales**: `admin` / `admin123`

## Ejemplo con Thunder Client

### Crear una plataforma (con auth):
```
POST http://localhost:8080/api/v1/plataformas
Auth: Basic admin / admin123
Body (JSON):
{
  "nombre": "Xbox Series X",
  "fabricante": "Microsoft",
  "generacion": "Novena"
}
```

### Listar videojuegos (sin auth):
```
GET http://localhost:8080/api/v1/videojuegos
```
