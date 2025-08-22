package com.jule.studentmanagement.infrastructure.mapper;

import com.jule.studentmanagement.infrastructure.dto.CountryInfoDTO;
import com.jule.studentmanagement.infrastructure.soap.ws.client.TCountryInfo;
import org.springframework.stereotype.Component;

@Component
public class CountryInfoMapper {

    public CountryInfoDTO toDTO(TCountryInfo countryInfo) {
        if (countryInfo == null) {
            return null;
        }

        return CountryInfoDTO.builder()
                .isoCode(countryInfo.getSISOCode())
                .name(countryInfo.getSName())
                .capitalCity(countryInfo.getSCapitalCity())
                .currencyIsoCode(countryInfo.getSCurrencyISOCode())
                .phoneCode(countryInfo.getSPhoneCode())
                .continentCode(countryInfo.getSContinentCode())
                .flag(countryInfo.getSCountryFlag())
                .languages(countryInfo.getLanguages())
                .build();
    }
}