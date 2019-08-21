package com.epam.rd.testing.service;

import com.epam.rd.testing.repository.TransactionRepository;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.service.dto.TransactionDTO;
import com.epam.rd.testing.service.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public Collection<TransactionDTO> findAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toDomain(transactionDTO);
        transactionRepository.save(transaction);
    }

    @Override
    public void removeTransaction(String transactionId) {
        transactionRepository.deleteById(UUID.fromString(transactionId));
    }

    @Override
    public void updateTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.toDomain(transactionDTO);
        transactionRepository.save(transaction);
    }
}
