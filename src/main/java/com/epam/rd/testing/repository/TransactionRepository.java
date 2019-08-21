package com.epam.rd.testing.repository;

import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.repository.enums.Currency;
import com.epam.rd.testing.repository.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Collection<Transaction> findTransactionByTransactionStatus(TransactionStatus transactionStatus);
    Collection<Transaction> findByCurrencyOrderByLocalDateTimeAsc(Currency currency);
}
