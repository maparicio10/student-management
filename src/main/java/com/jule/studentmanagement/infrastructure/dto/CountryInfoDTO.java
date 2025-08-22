package com.jule.studentmanagement.infrastructure.dto;

import com.jule.studentmanagement.infrastructure.soap.ws.client.ArrayOftLanguage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del país obtenida desde servicio SOAP")
public class CountryInfoDTO {

    @Schema(description = "Código ISO del país", example = "US")
    private String isoCode;

    @Schema(description = "Nombre del país", example = "United States")
    private String name;

    @Schema(description = "Nombre de la capital", example = "Washington")
    private String capitalCity;

    @Schema(description = "Código de moneda", example = "USD")
    private String currencyIsoCode;

    @Schema(description = "Código telefónico del país", example = "1")
    private String phoneCode;
    private String continentCode;
    private String flag;
    private ArrayOftLanguage languages;
}
