package com.epam.rd.testing.controller;

import com.epam.rd.testing.SpringTests;
import com.epam.rd.testing.repository.TransactionRepository;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.repository.enums.TransactionStatus;
import com.epam.rd.testing.service.dto.TransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencySaleIT extends SpringTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void transactionCreationWorksThroughAllLayers() throws Exception {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(null)
                .profileId("profileId1")
                .currency("EUR")
                .rate(1.34D)
                .amount(BigDecimal.TEN)
                .transactionStatus("PENDING")
                .localDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        mockMvc.perform(post("/transactions/transaction")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isOk());

        List<Transaction> transactions = transactionRepository.findAll();
        Transaction transaction = transactions.stream()
                .findFirst()
                .orElse(null);

        assertThat(transaction, is(not(nullValue())));
        assertThat(transaction.getId(), is(notNullValue()));
        assertThat(transaction.getProfileId(), is(transactionDTO.getProfileId()));
        assertThat(transaction.getCurrency(), is(Currency.valueOf(transactionDTO.getCurrency())));
        assertThat(transaction.getRate(), is(transactionDTO.getRate()));
        assertThat(transaction.getAmount(), comparesEqualTo(transactionDTO.getAmount()));
        assertThat(transaction.getTransactionStatus(), is(TransactionStatus.valueOf(transactionDTO.getTransactionStatus())));
    }

}
