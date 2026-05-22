## Descripción del proyecto

Proyecto de API REST desarrollada con Spring Boot para gestionar una tienda de videojuegos.

Incluye gestión de videojuegos, plataformas, clientes y pedidos con persistencia en base de datos mediante JPA/Hibernate.

Trabajo de la unidad ut6 realizado por Aythami Reyes y Carlos Perdomo.

## Estructura del proyecto

- Controller: Endpoints REST.
- Service: Lógica de negocio.
- Repository: Acceso a datos.
- Model: Entidades JPA.

## Entidades principales

- Videojuego
- Plataforma
- Cliente
- Pedido

## Endpoints principales

### Videojuegos
- GET /api/videojuegos Listar todos
- GET /api/videojuegos/{id} Obtener por ID
- POST /api/videojuegos Crear
- PUT /api/videojuegos/{id} Actualizar
- DELETE /api/videojuegos/{id} Eliminar

### Plataformas
- GET /api/plataformas Listar todas
- GET /api/plataformas/{id} Obtener por ID
- POST /api/plataformas Crear
- PUT /api/plataformas/{id} Actualizar
- DELETE /api/plataformas/{id} Eliminar

### Clientes
- GET /api/clientes Listar todos
- GET /api/clientes/{id} Obtener por ID
- POST /api/clientes Crear
- PUT /api/clientes/{id} Actualizar
- DELETE /api/clientes/{id} Eliminar

### Pedidos
- GET /api/pedidos Listar todos
- GET /api/pedidos/{id} Obtener por ID
- POST /api/pedidos Crear
- PUT /api/pedidos/{id} Actualizar
- DELETE /api/pedidos/{id} Eliminar

## Base de datos

## Tecnologías utilizadas
