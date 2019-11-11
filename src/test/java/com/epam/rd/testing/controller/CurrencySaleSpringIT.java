package com.epam.rd.testing.controller;

import com.epam.rd.testing.BaseSpringIntegrationTest;
import com.epam.rd.testing.service.dto.CurrencyExchangeRateResponse;
import com.epam.rd.testing.service.dto.TransactionRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;

import static com.epam.rd.testing.utils.TestDataGenerator.generateRequestTransactionDtos;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CurrencySaleSpringIT extends BaseSpringIntegrationTest {

    @Test
    @Transactional
    public void transactionCreationWorksThroughAllLayersWhenExceptionIsThrown() throws Exception {
        //Given
        TransactionRequestDto transactionDTO = generateRequestTransactionDtos(1).get(0);
        Mockito.when(restTemplate.getForObject(any(URI.class), eq(CurrencyExchangeRateResponse.class)))
                .thenThrow(new RuntimeException("Connection error has been occurred"));

        mockMvc.perform(post("/transactions/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/transactions")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].profileId", is(transactionDTO.getProfileId())))
                .andExpect(jsonPath("$[0].currency", is(transactionDTO.getCurrency())))
                .andExpect(jsonPath("$[0].rate", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].transactionStatus", is(transactionDTO.getTransactionStatus())));
    }

    @Test
    @Transactional
    public void transactionCreationWorksThroughAllLayers() throws Exception {
        //Given
        TransactionRequestDto transactionDTO = generateRequestTransactionDtos(1).get(0);
        Mockito.when(restTemplate.getForObject(any(URI.class), eq(CurrencyExchangeRateResponse.class)))
                .thenReturn(readFromJson());

        mockMvc.perform(post("/transactions/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/transactions")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].profileId", is(transactionDTO.getProfileId())))
                .andExpect(jsonPath("$[0].currency", is(transactionDTO.getCurrency())))
                .andExpect(jsonPath("$[0].rate", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].transactionStatus", is(transactionDTO.getTransactionStatus())));
    }


    private CurrencyExchangeRateResponse readFromJson() {
        ClassPathResource classPathResource = new ClassPathResource("/responses/privat24_response.json");
        try {
            return objectMapper.readValue(classPathResource.getInputStream(), CurrencyExchangeRateResponse.class);
        } catch (IOException e) {
            log.error(String.format("Cannot read from file: %s", e.getMessage()), e);
        }

        return null;
    }
}
