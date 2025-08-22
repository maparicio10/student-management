package com.jule.studentmanagement;

import com.jule.studentmanagement.infrastructure.dto.CountryInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
@Slf4j
public class ExternalSoapService {

    private final WebServiceTemplate webServiceTemplate;
    private final String countryServiceUrl;

    public ExternalSoapService(WebServiceTemplate webServiceTemplate,
                               @Value("${external.soap.countryinfo.url}") String countryServiceUrl) {
        this.webServiceTemplate = webServiceTemplate;
        this.countryServiceUrl = countryServiceUrl;
    }

    public CountryInfoDTO getCountryInfo(String countryCode) {
        try {
            log.debug("Obteniendo información del país: {}", countryCode);

            // Como el servicio SOAP real es complejo, simulamos la respuesta
            // En un proyecto real, aquí usarías las clases generadas por JAXB
            CountryInfoDTO countryInfo = simulateCountryInfo(countryCode);

            log.debug("Información del país obtenida: {}", countryInfo);
            return countryInfo;

        } catch (Exception e) {
            log.error("Error al obtener información del país {}: {}", countryCode, e.getMessage());
            return createDefaultCountryInfo(countryCode);
        }
    }

    private CountryInfoDTO simulateCountryInfo(String countryCode) {
        // Simulación de respuesta SOAP - en un proyecto real usarías el cliente SOAP generado
        CountryInfoDTO info = new CountryInfoDTO();
        info.setIsoCode(countryCode);

        switch (countryCode.toUpperCase()) {
            case "US":
                info.setName("United States");
                info.setCapitalCity("Washington");
                info.setCurrencyIsoCode("USD");
                info.setPhoneCode("1");
                break;
            case "DO":
                info.setName("Dominican Republic");
                info.setCapitalCity("Santo Domingo");
                info.setCurrencyIsoCode("DOP");
                info.setPhoneCode("1");
                break;
            case "ES":
                info.setName("Spain");
                info.setCapitalCity("Madrid");
                info.setCurrencyIsoCode("EUR");
                info.setPhoneCode("34");
                break;
            default:
                info.setName("Unknown Country");
                info.setCapitalCity("Unknown");
                info.setCurrencyIsoCode("XXX");
                info.setPhoneCode("0");
        }

        return info;
    }

    private CountryInfoDTO createDefaultCountryInfo(String countryCode) {
        CountryInfoDTO info = new CountryInfoDTO();
        info.setIsoCode(countryCode);
        info.setName("Unknown Country");
        info.setCapitalCity("Unknown");
        info.setCurrencyIsoCode("XXX");
        info.setPhoneCode("0");
        return info;
    }
}
