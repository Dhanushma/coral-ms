package com.coral.service;

import com.coral.dto.TransactionResponseDTO;

import java.time.LocalDate;

public interface TransactionService {

    TransactionResponseDTO getTransactionsByAccountNumberAndDateRange(
            String accountNumber, LocalDate fromDate, LocalDate toDate, int page, int size);
}
