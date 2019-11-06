package com.epam.rd.testing.controller;

import com.epam.rd.testing.BaseSpringIntegrationTest;
import com.epam.rd.testing.service.dto.TransactionDTO;
import org.junit.Test;

import static com.epam.rd.testing.utils.TestDataGenerator.generateTransactionDtos;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrencySaleSpringIT extends BaseSpringIntegrationTest {

    private static final double ERROR_RANGE = 0.0001;

    @Test
    public void transactionCreationWorksThroughAllLayers() throws Exception {
        TransactionDTO transactionDTO = generateTransactionDtos(1).get(0);

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
                .andExpect(jsonPath("$[0].rate", is(closeTo(transactionDTO.getRate(), ERROR_RANGE))))
                .andExpect(jsonPath("$[0].transactionStatus", is(transactionDTO.getTransactionStatus())));
    }

}
