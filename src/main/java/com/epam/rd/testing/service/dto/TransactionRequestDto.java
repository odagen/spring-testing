package com.epam.rd.testing.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequestDto {

    @JsonProperty
    private String id;

    @JsonProperty
    private String profileId;

    @JsonProperty
    private String currency;

    @JsonProperty
    private BigDecimal amount;

    @JsonProperty
    private String transactionStatus;

    @JsonProperty
    private LocalDateTime localDateTime;
}
