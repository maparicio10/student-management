package com.jule.studentmanagement.infrastructure.config;

import com.jule.studentmanagement.infrastructure.controller.StudentResource;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.ApplicationPath;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Student Management API", version = "1.0", description = "Documentación de endpoints de estudiantes")
)
@Configuration
@ApplicationPath("/api")
@Slf4j
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        log.info("Configurando Jersey...");

        packages("com.jule.studentmanagement");


        // Registrar recursos REST
        register(StudentResource.class);

        // Configurar Jackson para JSON
//        packages("com.fasterxml.jackson.jaxrs.json");
        // Registra los recursos de OpenAPI
        register(OpenApiResource.class);
        register(AcceptHeaderOpenApiResource.class);

        log.info("Jersey configurado exitosamente");
    }
}
