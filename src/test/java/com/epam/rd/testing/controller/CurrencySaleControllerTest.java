package com.epam.rd.testing.controller;

import com.epam.rd.testing.service.TransactionService;
import com.epam.rd.testing.service.dto.TransactionResponseDto;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.epam.rd.testing.utils.TestDataGenerator.generateResponseTransactionDtos;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CurrencySaleControllerTest {
    // Mocking dependant service
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);
    // Injecting mocked service providing it as constructor argument
    private final CurrencySaleController sut = new CurrencySaleController(transactionService);
    //Configuring mockMvc tool
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();

    @Test
    public void getAllTransactionsShouldReturnTransactionsDetails() throws Exception {
        List<TransactionResponseDto> inputTransactionDtos = generateResponseTransactionDtos(2);
        given(this.transactionService.findAllTransactions()).willReturn(inputTransactionDtos);

        mockMvc.perform(get("/transactions")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].profileId", is(inputTransactionDtos.get(0).getProfileId())))
                .andExpect(jsonPath("$[1].profileId", is(inputTransactionDtos.get(1).getProfileId())))
                .andExpect(jsonPath("$[0].currency", is(inputTransactionDtos.get(0).getCurrency())))
                .andExpect(jsonPath("$[1].currency", is(inputTransactionDtos.get(1).getCurrency())))
                .andExpect(jsonPath("$[0].transactionStatus", is(inputTransactionDtos.get(0).getTransactionStatus())))
                .andExpect(jsonPath("$[1].transactionStatus", is(inputTransactionDtos.get(1).getTransactionStatus())));
    }
}
