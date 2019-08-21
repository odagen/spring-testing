package com.epam.rd.testing.service;

import com.epam.rd.testing.service.dto.TransactionDTO;

import java.util.Collection;

public interface TransactionService {

    Collection<TransactionDTO> findAllTransactions();

    void createTransaction(TransactionDTO transactionDTO);

    void removeTransaction(String transactionId);

    void updateTransaction(TransactionDTO transactionDTO);
}
