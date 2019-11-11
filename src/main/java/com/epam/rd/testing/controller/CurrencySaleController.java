package com.epam.rd.testing.controller;

import com.epam.rd.testing.service.TransactionService;
import com.epam.rd.testing.service.dto.TransactionRequestDto;
import com.epam.rd.testing.service.dto.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/transactions")
public class CurrencySaleController {

    private final TransactionService transactionService;

    @Autowired
    public CurrencySaleController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Collection<TransactionResponseDto> getAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @PostMapping(path = "/transaction")
    public void createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        transactionService.createTransaction(transactionRequestDto);
    }

    @PutMapping(path = "/transaction")
    public void updateTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        transactionService.updateTransaction(transactionRequestDto);
    }

    @DeleteMapping(path = "/transaction/{transactionId}")
    public void deleteTransaction(String transactionId) {
        transactionService.removeTransaction(transactionId);
    }
}
