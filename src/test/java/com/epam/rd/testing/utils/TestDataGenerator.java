package com.epam.rd.testing.utils;

import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.repository.enums.TransactionStatus;
import com.epam.rd.testing.service.dto.TransactionRequestDto;
import com.epam.rd.testing.service.dto.TransactionResponseDto;
import com.epam.rd.testing.service.mapper.TransactionMapperImpl;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class TestDataGenerator {
    private final Random random = new Random();
    private final TransactionMapperImpl transactionMapper = new TransactionMapperImpl();

    public static List<TransactionRequestDto> generateRequestTransactionDtos(int count) {
        return generateTransactions(count).stream()
                .map(transactionMapper::toRequest)
                .collect(Collectors.toList());
    }

    public static List<TransactionResponseDto> generateResponseTransactionDtos(int count) {
        return generateTransactions(count).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    public static List<Transaction> generateTransactions(int count) {
        return IntStream.range(0, count)
                .mapToObj(TestDataGenerator::createTransaction)
                .collect(Collectors.toList());
    }

    private Transaction createTransaction(int counter) {
        return Transaction.builder().currency(random.nextBoolean() ? Currency.USD : Currency.EUR)
                .id(UUID.randomUUID())
                .amount(BigDecimal.valueOf(counter))
                .rate(random.nextDouble())
                .profileId(RandomStringUtils.randomAlphabetic(10))
                .transactionStatus(random.nextBoolean() ? TransactionStatus.COMPLETED : TransactionStatus.PENDING)
                .localDateTime(LocalDateTime.now())
                .rate(random.nextDouble())
                .build();
    }

}
