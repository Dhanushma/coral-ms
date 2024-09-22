package com.coral.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private long transactionId;
    private BigDecimal transactionAmount;
    private LocalDate transactionDate;
}
