package com.jule.studentmanagement.infrastructure.controller;

import com.jule.studentmanagement.application.service.StudentService;
import com.jule.studentmanagement.infrastructure.dto.ApiResponseDTO;
import com.jule.studentmanagement.infrastructure.dto.StudentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Path("/students")
@Component
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Estudiantes", description = "API para gestión de estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService studentService;

    @GET
    @Operation(summary = "Obtener todos los estudiantes",
            description = "Obtiene una lista paginada de todos los estudiantes con filtros opcionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getAllStudents(
            @Parameter(description = "Nombre a buscar") @QueryParam("name") String name,
            @Parameter(description = "Email a buscar") @QueryParam("email") String email,
            @Parameter(description = "Código de país") @QueryParam("countryCode") String countryCode,
            @Parameter(description = "Estado activo") @QueryParam("active") Boolean active,
            @Parameter(description = "Número de página") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Tamaño de página") @QueryParam("size") @DefaultValue("10") int size,
            @Parameter(description = "Campo de ordenamiento") @QueryParam("sort") @DefaultValue("id") String sort,
            @Parameter(description = "Dirección de ordenamiento") @QueryParam("direction") @DefaultValue("asc") String direction) {

        log.info("GET /students - page: {}, size: {}, filters: name={}, email={}, countryCode={}, active={}",
                page, size, name, email, countryCode, active);

        try {
            Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                    ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

            Page<StudentDTO> students = studentService.getStudentsWithFilters(
                    name, email, countryCode, active, pageable);

            ApiResponseDTO<Page<StudentDTO>> response = ApiResponseDTO.success(
                    students, "Estudiantes obtenidos exitosamente");

            return Response.ok(response).build();

        } catch (Exception e) {
            log.error("Error al obtener estudiantes: ", e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error("Error al obtener estudiantes");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener estudiante por ID",
            description = "Obtiene un estudiante específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado",
                    content = @Content(schema = @Schema(implementation = StudentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response getStudentById(
            @Parameter(description = "ID del estudiante", required = true) @PathParam("id") Long id) {

        log.info("GET /students/{}", id);

        try {
            StudentDTO student = studentService.getStudentById(id);
            ApiResponseDTO<StudentDTO> response = ApiResponseDTO.success(
                    student, "Estudiante obtenido exitosamente");
            return Response.ok(response).build();

        } catch (Exception e) {
            log.error("Error al obtener estudiante con ID {}: ", id, e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
    }

    @POST
    @Operation(summary = "Crear nuevo estudiante",
            description = "Crea un nuevo estudiante en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente",
                    content = @Content(schema = @Schema(implementation = StudentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response createStudent(@Valid StudentDTO studentDTO) {
        log.info("POST /students - Crear estudiante: {}", studentDTO.getEmail());

        try {
            StudentDTO createdStudent = studentService.createStudent(studentDTO);
            ApiResponseDTO<StudentDTO> response = ApiResponseDTO.success(
                    createdStudent, "Estudiante creado exitosamente");
            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (Exception e) {
            log.error("Error al crear estudiante: ", e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar estudiante",
            description = "Actualiza los datos de un estudiante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = StudentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response updateStudent(
            @Parameter(description = "ID del estudiante", required = true) @PathParam("id") Long id,
            @Valid StudentDTO studentDTO) {

        log.info("PUT /students/{} - Actualizar estudiante", id);

        try {
            StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
            ApiResponseDTO<StudentDTO> response = ApiResponseDTO.success(
                    updatedStudent, "Estudiante actualizado exitosamente");
            return Response.ok(response).build();

        } catch (Exception e) {
            log.error("Error al actualizar estudiante con ID {}: ", id, e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar estudiante",
            description = "Elimina un estudiante del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response deleteStudent(
            @Parameter(description = "ID del estudiante", required = true) @PathParam("id") Long id) {

        log.info("DELETE /students/{}", id);

        try {
            studentService.deleteStudent(id);
            return Response.noContent().build();

        } catch (Exception e) {
            log.error("Error al eliminar estudiante con ID {}: ", id, e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
    }

    @POST
    @Path("/{id}/deactivate")
    @Operation(summary = "Desactivar estudiante",
            description = "Desactiva un estudiante sin eliminarlo del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante desactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public Response deactivateStudent(
            @Parameter(description = "ID del estudiante", required = true) @PathParam("id") Long id) {

        log.info("POST /students/{}/deactivate", id);

        try {
            studentService.deactivateStudent(id);
            ApiResponseDTO<Void> response = ApiResponseDTO.success(
                    null, "Estudiante desactivado exitosamente");
            return Response.ok(response).build();

        } catch (Exception e) {
            log.error("Error al desactivar estudiante con ID {}: ", id, e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/active")
    @Operation(summary = "Obtener estudiantes activos",
            description = "Obtiene todos los estudiantes que están activos")
    public Response getActiveStudents() {
        log.info("GET /students/active");

        try {
            List<StudentDTO> activeStudents = studentService.getActiveStudents();
            ApiResponseDTO<List<StudentDTO>> response = ApiResponseDTO.success(
                    activeStudents, "Estudiantes activos obtenidos exitosamente");
            return Response.ok(response).build();

        } catch (Exception e) {
            log.error("Error al obtener estudiantes activos: ", e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error("Error al obtener estudiantes activos");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    @GET
    @Path("/search")
    @Operation(summary = "Buscar estudiantes por nombre",
            description = "Busca estudiantes por nombre o apellido")
    public Response searchStudentsByName(
            @Parameter(description = "Nombre a buscar", required = true) @QueryParam("name") String name) {

        log.info("GET /students/search?name={}", name);

        if (name == null || name.trim().isEmpty()) {
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error("El parámetro 'name' es requerido");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }

        try {
            List<StudentDTO> students = studentService.searchStudentsByName(name.trim());
            ApiResponseDTO<List<StudentDTO>> response = ApiResponseDTO.success(
                    students, "Búsqueda completada exitosamente");
            return Response.ok(response).build();

        } catch (Exception e) {
            log.error("Error al buscar estudiantes por nombre '{}': ", name, e);
            ApiResponseDTO<Void> errorResponse = ApiResponseDTO.error("Error en la búsqueda");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
}
