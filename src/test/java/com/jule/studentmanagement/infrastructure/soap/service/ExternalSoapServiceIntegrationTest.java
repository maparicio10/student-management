package com.jule.studentmanagement.infrastructure.soap.service;

import com.jule.studentmanagement.infrastructure.dto.CountryInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "external.soap.countryinfo.url=http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso"
})
class ExternalSoapServiceIntegrationTest {

    @Autowired
    private ExternalSoapService externalSoapService;

    @Test
    void shouldGetCountryInfo() {
        // Given
        String countryCode = "US";

        // When
        CountryInfoDTO result = externalSoapService.getCountryInfo(countryCode);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIsoCode()).isEqualTo(countryCode);
        assertThat(result.getName()).isNotBlank();
    }

    @Test
    void shouldGetCountryName() {
        // Given
        String countryCode = "ES";

        // When
        String result = externalSoapService.getCountryName(countryCode);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).contains("Spain");
    }

    @Test
    void shouldHandleInvalidCountryCode() {
        // Given
        String invalidCode = "XX";

        // When
        CountryInfoDTO result = externalSoapService.getCountryInfo(invalidCode);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Country not found in the database");
    }
}
