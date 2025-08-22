package com.jule.studentmanagement.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta estándar de la API")
public class ApiResponseDTO<T> {

    @Schema(description = "Indica si la operación fue exitosa", example = "true")
    private boolean success;

    @Schema(description = "Mensaje descriptivo")
    private String message;

    @Schema(description = "Datos de respuesta")
    private T data;

    @Schema(description = "Código de error (si aplica)")
    private String errorCode;

    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return new ApiResponseDTO<>(true, message, data, null);
    }

    public static <T> ApiResponseDTO<T> success(T data) {
        return success(data, "Operación exitosa");
    }

    public static <T> ApiResponseDTO<T> error(String message, String errorCode) {
        return new ApiResponseDTO<>(false, message, null, errorCode);
    }

    public static <T> ApiResponseDTO<T> error(String message) {
        return error(message, "GENERAL_ERROR");
    }
}
