package com.epam.rd.testing.service;

import com.epam.rd.testing.service.dto.TransactionRequestDto;
import com.epam.rd.testing.service.dto.TransactionResponseDto;

import java.util.Collection;

public interface TransactionService {

    Collection<TransactionResponseDto> findAllTransactions();

    void createTransaction(TransactionRequestDto transactionRequestDto);

    void removeTransaction(String transactionId);

    void updateTransaction(TransactionRequestDto transactionDto);
}
