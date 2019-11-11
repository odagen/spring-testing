package com.epam.rd.testing.service;

import com.epam.rd.testing.repository.TransactionRepository;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.service.dto.ExchangeRate;
import com.epam.rd.testing.service.dto.TransactionRequestDto;
import com.epam.rd.testing.service.dto.TransactionResponseDto;
import com.epam.rd.testing.service.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CurrencyExchangeRateService currencyRateService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Collection<TransactionResponseDto> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void createTransaction(TransactionRequestDto transactionRequest) {
        Transaction transaction = transactionMapper.toDomain(transactionRequest);
        ExchangeRate exchangeRate = currencyRateService.tryGetCurrencyExchangeRate(transaction.getCurrency(), LocalDate.now())
                .orElseGet(() -> currencyRateService.getCurrencyExchangeRateFallBack(transaction.getCurrency()));
        transaction.setRate(exchangeRate.getSaleRate());

        transactionRepository.save(transaction);
    }

    @Override
    public void removeTransaction(String transactionId) {
        transactionRepository.deleteById(UUID.fromString(transactionId));
    }

    @Override
    public void updateTransaction(TransactionRequestDto transactionDto) {
        Transaction transaction = transactionMapper.toDomain(transactionDto);
        transactionRepository.save(transaction);
    }
}
