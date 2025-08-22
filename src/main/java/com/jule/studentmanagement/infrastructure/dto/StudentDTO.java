package com.jule.studentmanagement.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estudiante")
public class StudentDTO {

    @Schema(description = "ID único del estudiante", example = "1")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Schema(description = "Nombre del estudiante", example = "Juan", required = true)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Schema(description = "Apellido del estudiante", example = "Pérez", required = true)
    private String lastName;

    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Correo electrónico del estudiante", example = "juan.perez@email.com", required = true)
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Fecha de nacimiento", example = "1995-05-15", required = true)
    private LocalDate birthDate;

    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    @Schema(description = "Número de teléfono", example = "+1234567890")
    private String phone;

    @Size(max = 200, message = "La dirección no puede tener más de 200 caracteres")
    @Schema(description = "Dirección del estudiante", example = "Calle Principal 123")
    private String address;

    @Schema(description = "Código de país ISO", example = "US")
    private String countryCode;

    @Schema(description = "ID del post externo asociado", example = "1")
    private Long externalPostId;

    @Schema(description = "Fecha y hora de creación", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha y hora de última actualización", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Indica si el estudiante está activo", example = "true")
    private Boolean active;

    // Información enriquecida desde servicios externos
    @Schema(description = "Información del país obtenida desde servicio SOAP")
    private CountryInfoDTO countryInfo;

    @Schema(description = "Post asociado obtenido desde servicio REST")
    private ExternalPostDTO externalPost;
}
