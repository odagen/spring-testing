package com.epam.rd.testing.controller;

import com.epam.rd.testing.BaseTest;
import com.epam.rd.testing.service.TransactionService;
import com.epam.rd.testing.service.dto.TransactionDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.epam.rd.testing.utils.TestDataGenerator.generateTransactionDtos;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Example of MVC test using spring-boot WebMvcTest slice
 */
@WebMvcTest(controllers = CurrencySaleController.class)
public class CurrencySaleControllerWebMvcTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void getAllTransactionsShouldReturnTransactionsDetails() throws Exception {
        List<TransactionDTO> inputTransactionDtos = generateTransactionDtos(2);

        given(transactionService.findAllTransactions()).willReturn(inputTransactionDtos);

        mockMvc.perform(get("/transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].profileId", Matchers.is(inputTransactionDtos.get(0).getProfileId())))
                .andExpect(jsonPath("$[1].profileId", Matchers.is(inputTransactionDtos.get(1).getProfileId())))
                .andExpect(jsonPath("$[0].currency", Matchers.is(inputTransactionDtos.get(0).getCurrency())))
                .andExpect(jsonPath("$[1].currency", Matchers.is(inputTransactionDtos.get(1).getCurrency())))
                .andExpect(jsonPath("$[0].transactionStatus", Matchers.is(inputTransactionDtos.get(0).getTransactionStatus())))
                .andExpect(jsonPath("$[1].transactionStatus", Matchers.is(inputTransactionDtos.get(1).getTransactionStatus())));
    }
}
