package com.jule.studentmanagement.infrastructure.soap.service;

import com.jule.studentmanagement.infrastructure.dto.CountryInfoDTO;
import com.jule.studentmanagement.infrastructure.mapper.CountryInfoMapper;
import com.jule.studentmanagement.infrastructure.soap.ws.client.CountryInfoServiceSoapType;
import com.jule.studentmanagement.infrastructure.soap.ws.client.TCountryInfo;
import jakarta.xml.ws.WebServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalSoapService {

    private final CountryInfoServiceSoapType countryInfoSoapClient;
    private final CountryInfoMapper countryInfoMapper;

    @Cacheable(value = "countryInfo", key = "#countryCode")
    public CountryInfoDTO getCountryInfo(String countryCode) {
        try {
            log.debug("Obteniendo información del país desde SOAP: {}", countryCode);

            // Llamada real al servicio SOAP
            TCountryInfo countryInfo = countryInfoSoapClient.fullCountryInfo(countryCode.toUpperCase());

            if (countryInfo == null) {
                log.warn("No se encontró información para el país: {}", countryCode);
                return createDefaultCountryInfo(countryCode);
            }

            CountryInfoDTO result = countryInfoMapper.toDTO(countryInfo);
            log.debug("Información del país obtenida exitosamente: {}", result);

            return result;

        } catch (WebServiceException e) {
            log.error("Error de servicio web al obtener información del país {}: {}",
                    countryCode, e.getMessage());
            return createDefaultCountryInfo(countryCode);
        } catch (Exception e) {
            log.error("Error inesperado al obtener información del país {}: {}",
                    countryCode, e.getMessage(), e);
            return createDefaultCountryInfo(countryCode);
        }
    }

    public List<String> getAllCountryCodes() {
        try {
            log.debug("Obteniendo todos los códigos de países");

            // Obtener lista de códigos ISO
            var countryCodeList = countryInfoSoapClient.listOfCountryNamesByCode();

            return countryCodeList.getTCountryCodeAndName()
                    .stream()
                    .map(country -> country.getSISOCode())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al obtener códigos de países: {}", e.getMessage());
            return List.of("US", "ES", "DO", "CA", "MX"); // Fallback
        }
    }

    public String getCountryName(String countryCode) {
        try {
            log.debug("Obteniendo nombre del país: {}", countryCode);

            String countryName = countryInfoSoapClient.countryName(countryCode.toUpperCase());

            if (countryName == null || countryName.trim().isEmpty()) {
                log.warn("Nombre no encontrado para el país: {}", countryCode);
                return "Unknown Country";
            }

            return countryName;

        } catch (Exception e) {
            log.error("Error al obtener nombre del país {}: {}", countryCode, e.getMessage());
            return "Unknown Country";
        }
    }

    public String getCapitalCity(String countryCode) {
        try {
            log.debug("Obteniendo capital del país: {}", countryCode);

            String capital = countryInfoSoapClient.capitalCity(countryCode.toUpperCase());

            if (capital == null || capital.trim().isEmpty()) {
                log.warn("Capital no encontrada para el país: {}", countryCode);
                return "Unknown Capital";
            }

            return capital;

        } catch (Exception e) {
            log.error("Error al obtener capital del país {}: {}", countryCode, e.getMessage());
            return "Unknown Capital";
        }
    }

    public String getCurrencyCode(String countryCode) {
        try {
            log.debug("Obteniendo código de moneda del país: {}", countryCode);

            String currency = String.valueOf(countryInfoSoapClient.countryCurrency(countryCode.toUpperCase()));

            if (currency == null || currency.trim().isEmpty()) {
                log.warn("Moneda no encontrada para el país: {}", countryCode);
                return "XXX";
            }

            return currency;

        } catch (Exception e) {
            log.error("Error al obtener moneda del país {}: {}", countryCode, e.getMessage());
            return "XXX";
        }
    }

    private CountryInfoDTO createDefaultCountryInfo(String countryCode) {
        return CountryInfoDTO.builder()
                .isoCode(countryCode.toUpperCase())
                .name("Unknown Country")
                .capitalCity("Unknown")
                .currencyIsoCode("XXX")
                .phoneCode("0")
                .continentCode("XX")
                .flag("")
                .languages(null)
                .build();
    }
}
