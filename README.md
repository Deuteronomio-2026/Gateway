# MindBridge — API Gateway

Microservicio de enrutamiento central de la plataforma MindBridge.
Redirige las peticiones HTTP hacia los microservicios correspondientes.

## Tecnologías
- Java 17
- Spring Cloud Gateway
- Spring Boot 3.2.5
- Maven
- Docker

## Rutas disponibles

| Ruta | Microservicio destino |
|------|-----------------------|
| `/api/users/**` | UserService |
| `/api/sessions/**` | SchedulingService |
| `/api/video/**` | VideoService |
| `/api/offers/**` | OfferService |

## Correr con Docker
```bash
docker-compose up
```

El gateway queda disponible en `http://localhost:8080`

## Verificar que está corriendo
```
http://localhost:8080/actuator/health
```

Respuesta esperada: `{"status":"UP"}`

## Pipeline CI

Cada push a `main` o `develop` ejecuta automáticamente:
1. Compilación con Maven
2. Pruebas unitarias
3. Reporte de cobertura con JaCoCo
