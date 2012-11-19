labs-opendata-adopta-playa-api
==============================

OpenData API for adopta una playa

# Ejemplos

## Lista de playas con paginación

POST

http://localhost:8080/labs-opendata-adopta-playa-api/api/playas/query

Payload

```json
{
    "offset":0,
    "limit":10,
    "query":"select p.nombre from Playa p",
    "properties":["playa"]
}
```

## Usuarios que más han contibuido

POST

http://localhost:8080/labs-opendata-adopta-playa-api/api/playas/query

Payload

```json
{
    "offset":0,
    "limit":10,
    "query":"select p.adoptadaPor, count(p) from Playa p group by p.adoptadaPor order by count(p) desc",
    "properties":["usuario", "totalPlayas"]
}
```

## Playas en un radio de 10 km cerca de Chiclana

POST

http://localhost:8080/labs-opendata-adopta-playa-api/api/playas/query

Payload

```json
{
    "offset":0,
    "limit":10,
    "centerLat":36.365457,
    "centerLng":-6.170368,
    "radiusKm":10
}
```
