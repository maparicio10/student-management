# Student Management API

Una API REST completa para la gestión de estudiantes desarrollada con Spring Boot y Jersey, que incluye integración con servicios externos REST y SOAP, documentación con Swagger, pruebas unitarias y manejo de excepciones.

## Requisitos previos
- Java 17 o superior
- Maven
- IDE recomendado: IntelliJ IDEA

## Instalación
1. Clona el repositorio:
   ```bash
   git clone <URL-del-repositorio>
   ```
2. Accede al directorio del proyecto:
   ```bash
   cd student-management
   ```
3. Compila el proyecto:
   ```bash
   mvn clean install
   ```

## Ejecución
Para iniciar la aplicación localmente:
```bash
mvn spring-boot:run
```
La API estará disponible en: `http://localhost:8080`

## Pruebas
Para ejecutar las pruebas unitarias y de integración:
```bash
mvn test
```

## Estructura del proyecto
- `src/main/java/com/jule/studentmanagement/`: Código fuente principal
- `src/test/java/com/jule/studentmanagement/`: Pruebas
- `src/main/resources/`: Archivos de configuración

## Integración con servicios externos
- **REST**: Consumo de APIs externas mediante Jersey
- **SOAP**: Integración con servicios SOAP para obtener información de países

## Documentación
La documentación de la API está disponible mediante Swagger/OpenAPI en:
```
http://localhost:8080/swagger-ui/
```

## Soporte
Para dudas o soporte, contacta al equipo de desarrollo.
