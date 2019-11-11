package com.epam.rd.testing.controller;

import com.epam.rd.testing.BaseSBIntegrationTest;
import com.epam.rd.testing.service.dto.CurrencyExchangeRateResponse;
import com.epam.rd.testing.service.dto.TransactionRequestDto;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import static com.epam.rd.testing.utils.TestDataGenerator.generateRequestTransactionDtos;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrencySaleSpringBootIT extends BaseSBIntegrationTest {

    // MockMvc and ObjectMapper are injected in the parent class

    @Test
    @Transactional
    public void transactionCreationWorksThroughAllLayersWhenExceptionIsThrown() throws Exception {
        //Given
        TransactionRequestDto transactionDTO = generateRequestTransactionDtos(1).get(0);

        doThrow(new RuntimeException("Some connection error"))
                .when(restTemplate)
                .getForObject(any(), eq(CurrencyExchangeRateResponse.class));

        //When
        mockMvc.perform(post("/transactions/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());

        //Then
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
    public void transactionCreationWorksThroughAllLayersWhenException() throws Exception {
        //Given
        TransactionRequestDto transactionDTO = generateRequestTransactionDtos(1).get(0);

        doReturn(readFromJson())
                .when(restTemplate)
                .getForObject(any(), eq(CurrencyExchangeRateResponse.class));

        //When
        mockMvc.perform(post("/transactions/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());

        //Then
        mockMvc.perform(get("/transactions")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].profileId", is(transactionDTO.getProfileId())))
                .andExpect(jsonPath("$[0].currency", is(transactionDTO.getCurrency())))
                .andExpect(jsonPath("$[0].rate", is(not(nullValue()))))
                .andExpect(jsonPath("$[0].transactionStatus", is(transactionDTO.getTransactionStatus())));
    }

    @SneakyThrows
    private CurrencyExchangeRateResponse readFromJson() {
        ClassPathResource classPathResource = new ClassPathResource("/responses/privat24_response.json");
        return objectMapper.readValue(classPathResource.getInputStream(), CurrencyExchangeRateResponse.class);
    }

}
