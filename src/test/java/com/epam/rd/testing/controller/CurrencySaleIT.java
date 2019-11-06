package com.epam.rd.testing.controller;

import com.epam.rd.testing.BaseIntegrationTest;
import com.epam.rd.testing.service.dto.TransactionDTO;
import org.junit.Test;

import static com.epam.rd.testing.utils.TestDataGenerator.generateTransactionDtos;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrencySaleIT extends BaseIntegrationTest {

    // MockMvc and ObjectMapper are injected in the parent class

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
                .andExpect(jsonPath("$[0].rate", is(transactionDTO.getRate())))
                .andExpect(jsonPath("$[0].transactionStatus", is(transactionDTO.getTransactionStatus())));
    }

}
