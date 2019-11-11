package com.epam.rd.testing.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchangeRateResponse {

    @JsonProperty
    private String date;

    @JsonProperty("bank")
    private String bankName;

    @JsonProperty("baseCurrency")
    private Integer baseCurrencyCode;

    @JsonProperty("baseCurrencyLit")
    private String baseCurrencyLit;

    @JsonProperty("exchangeRate")
    private List<ExchangeRate> currencyRates = new ArrayList<>();
}
