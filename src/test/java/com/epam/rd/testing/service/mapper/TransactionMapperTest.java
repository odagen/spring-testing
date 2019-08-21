package com.epam.rd.testing.service.mapper;

import com.epam.rd.testing.SpringTests;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.repository.enums.TransactionStatus;
import com.epam.rd.testing.service.dto.TransactionDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TransactionMapperImpl.class)
@DirtiesContext
public class TransactionMapperTest extends SpringTests {

    @Autowired
    private TransactionMapperImpl transactionMapper;

    @Test
    public void mapperInitializedCorrectly() {
        assertThat(transactionMapper, is(notNullValue()));
    }

    @Test
    public void verifyTransactionMapperCorrectlyConvertsDomainToDto() {
        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .profileId("profileId")
                .currency(Currency.EUR)
                .rate(1.34D)
                .amount(BigDecimal.TEN)
                .transactionStatus(TransactionStatus.PENDING)
                .localDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        TransactionDTO transactionDTO = transactionMapper.toDTO(transaction);

        assertThat(transactionDTO, is(notNullValue()));
        assertThat(transactionDTO.getId(), is(transaction.getId().toString()));
        assertThat(transactionDTO.getProfileId(), is(transaction.getProfileId()));
        assertThat(transactionDTO.getCurrency(), is(transaction.getCurrency().toString()));
        assertThat(transactionDTO.getRate(), is(transaction.getRate()));
        assertThat(transactionDTO.getTransactionStatus(), is(transaction.getTransactionStatus().toString()));
    }

    @Test
    public void verifyTransactionMapperCorrectlyConvertsDtoToDomain() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(null)
                .profileId("profileId")
                .currency("EUR")
                .rate(1.34D)
                .amount(BigDecimal.TEN)
                .transactionStatus("PENDING")
                .localDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        Transaction transaction = transactionMapper.toDomain(transactionDTO);

        assertThat(transaction, is(notNullValue()));
        assertThat(transaction.getId(), is(notNullValue()));
        assertThat(transaction.getProfileId(), is(transactionDTO.getProfileId()));
        assertThat(transaction.getCurrency(), is(Currency.valueOf(transactionDTO.getCurrency())));
        assertThat(transaction.getRate(), is(transactionDTO.getRate()));
        assertThat(transaction.getTransactionStatus(), is(TransactionStatus.valueOf(transactionDTO.getTransactionStatus())));
    }
}
