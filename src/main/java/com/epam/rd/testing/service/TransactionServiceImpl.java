package com.epam.rd.testing.service;

import com.epam.rd.testing.repository.TransactionRepository;
import com.epam.rd.testing.repository.entity.Transaction;
import com.epam.rd.testing.service.dto.TransactionDTO;
import com.epam.rd.testing.service.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

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
