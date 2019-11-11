package com.epam.rd.testing.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

    @JsonProperty
    private String baseCurrency;

    @JsonProperty
    private String currency;

    @JsonProperty
    private Double saleRate;

    @JsonProperty
    private Double purchaseRate;
}
