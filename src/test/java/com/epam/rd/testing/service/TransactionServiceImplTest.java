package com.epam.rd.testing.service;

import com.epam.rd.testing.repository.TransactionRepository;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.service.dto.TransactionDTO;
import com.epam.rd.testing.service.mapper.TransactionMapper;
import com.epam.rd.testing.service.mapper.TransactionMapperImpl;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static com.epam.rd.testing.utils.TestDataGenerator.generateTransactions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TransactionServiceImplTest {
    private final Random random = new Random();
    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private final TransactionMapper transactionMapper = new TransactionMapperImpl();
    private final TransactionService sut = new TransactionServiceImpl(transactionRepository, transactionMapper);

    @Test
    public void shouldFindAllPersistenceEntity() {
        //Given
        List<Transaction> transactions = generateTransactions(10);
        doReturn(transactions).when(transactionRepository).findAll();

        //When
        Collection<TransactionDTO> foundTransactions = sut.findAllTransactions();

        //Then
        verify(transactionRepository).findAll();
        assertThat(foundTransactions, hasSize(10));

        transactions.forEach(
                transaction -> assertThat(foundTransactions, hasItem(allOf(
                        hasProperty("id", is(transaction.getId().toString())),
                        hasProperty("profileId", is(transaction.getProfileId())),
                        hasProperty("currency", is(transaction.getCurrency().name())),
                        hasProperty("transactionStatus", is(transaction.getTransactionStatus().name())),
                        hasProperty("amount", is(transaction.getAmount())),
                        hasProperty("localDateTime", is(transaction.getLocalDateTime())),
                        hasProperty("rate", is(transaction.getRate()))))
                )
        );

    }
}
