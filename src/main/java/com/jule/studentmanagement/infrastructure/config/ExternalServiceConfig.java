package com.jule.studentmanagement.infrastructure.config;

import com.jule.studentmanagement.infrastructure.soap.ws.client.CountryInfoService;
import com.jule.studentmanagement.infrastructure.soap.ws.client.CountryInfoServiceSoapType;
import jakarta.xml.ws.BindingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Configuration
public class ExternalServiceConfig {

    @Value("${external.soap.countryinfo.url:http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso}")
    private String countryServiceUrl;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMessageSender(new HttpComponentsMessageSender());
        return webServiceTemplate;
    }

    @Bean
    public CountryInfoServiceSoapType countryInfoSoapClient() {
        CountryInfoService service = new CountryInfoService();
        CountryInfoServiceSoapType port = service.getCountryInfoServiceSoap();

        // Configurar la URL del endpoint
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                countryServiceUrl
        );

        // Configurar timeout (opcional)
        bindingProvider.getRequestContext().put(
                "com.sun.xml.ws.connect.timeout", 15000
        );
        bindingProvider.getRequestContext().put(
                "com.sun.xml.ws.request.timeout", 30000
        );

        return port;
    }
}
