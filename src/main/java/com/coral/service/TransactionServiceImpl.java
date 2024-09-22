package com.coral.service;

import com.coral.dto.TransactionDTO;
import com.coral.dto.TransactionResponseDTO;
import com.coral.entity.Transaction;
import com.coral.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionResponseDTO getTransactionsByAccountNumberAndDateRange(String accountNumber, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Transaction> transactionpage = transactionRepository.findTransactionsByAccountAndDateRange(accountNumber, fromDate, toDate, pageable);
            List<TransactionDTO> transactionDTOList = transactionpage.getContent().stream()
                    .map(transaction -> new TransactionDTO(
                            transaction.getId(),
                            transaction.getTransactionAmount(),
                            transaction.getTransactionDate()
                    )).toList();
            return new TransactionResponseDTO(transactionDTOList, transactionpage.isLast());
        } catch (Exception e) {
            log.error("Exception occurred while retrieving transactions {}", e.getMessage());
            throw new RuntimeException("Error retrieving transactions", e);
        }
    }
}
