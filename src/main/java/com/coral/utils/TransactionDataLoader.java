package com.coral.utils;

import com.coral.entity.Transaction;
import com.coral.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Random;

@Component
public class TransactionDataLoader implements CommandLineRunner {


    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Transaction Data");
        String accountNumber = "1234567890";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 1);
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            BigDecimal transactionAmount = BigDecimal.valueOf(random.nextDouble() * 1000) // Random amount between 0 and 1000
                    .setScale(2, RoundingMode.HALF_UP); // Set scale for decimal precision

            LocalDate transactionDate = startDate.plusDays(random.nextInt((int) (endDate.toEpochDay() - startDate.toEpochDay() + 1)));

            Transaction transaction = Transaction.builder()
                    .accountNumber(accountNumber)
                    .transactionAmount(transactionAmount)
                    .transactionDate(transactionDate)
                    .build();

            transactionRepository.save(transaction);

        }
    }
}
