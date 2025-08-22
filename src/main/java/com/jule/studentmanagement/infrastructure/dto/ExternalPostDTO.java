package com.jule.studentmanagement.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Post obtenido desde servicio REST externo")
public class ExternalPostDTO {

    @Schema(description = "ID del post", example = "1")
    private Long id;

    @Schema(description = "ID del usuario", example = "1")
    private Long userId;

    @Schema(description = "Título del post", example = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
    private String title;

    @Schema(description = "Cuerpo del post", example = "quia et suscipit...")
    private String body;
}
