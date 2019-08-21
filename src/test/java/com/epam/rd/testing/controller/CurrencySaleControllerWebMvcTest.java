package com.epam.rd.testing.controller;

import com.epam.rd.testing.SpringTests;
import com.epam.rd.testing.service.TransactionService;
import com.epam.rd.testing.service.dto.TransactionDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Example of MVC test using spring-boot WebMvcTest slice
 */
@WebMvcTest(controllers = CurrencySaleController.class)
public class CurrencySaleControllerWebMvcTest extends SpringTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void getAllTransactionsShouldReturnTransactionsDetails() throws Exception {
        TransactionDTO transactionDTO1 = TransactionDTO.builder()
                .id(null)
                .profileId("profileId1")
                .currency("EUR")
                .rate(1.34D)
                .amount(BigDecimal.TEN)
                .transactionStatus("PENDING")
                .localDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        TransactionDTO transactionDTO2 = TransactionDTO.builder()
                .id(null)
                .profileId("profileId2")
                .currency("USD")
                .rate(0.96D)
                .amount(BigDecimal.ONE)
                .transactionStatus("COMPLETED")
                .localDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();


        given(transactionService.findAllTransactions())
                .willReturn(Arrays.asList(transactionDTO1, transactionDTO2));

        mockMvc.perform(get("/transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].profileId", is(transactionDTO1.getProfileId())))
                .andExpect(jsonPath("$[1].profileId", is(transactionDTO2.getProfileId())))
                .andExpect(jsonPath("$[0].currency", is(transactionDTO1.getCurrency())))
                .andExpect(jsonPath("$[1].currency", is(transactionDTO2.getCurrency())));
    }
}
