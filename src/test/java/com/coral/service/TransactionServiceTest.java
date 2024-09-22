package com.coral.service;

import com.coral.dto.TransactionResponseDTO;
import com.coral.entity.Transaction;
import com.coral.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private final String ACC_NUM = "1234567890";
    private final LocalDate FROM_DATE = LocalDate.of(2024, 1, 1);
    private final LocalDate TO_DATE = LocalDate.of(2024, 2, 1);
    private final int PAGE = 0;
    private final int SIZE = 10;


    @Test
    public void testGetTransactionsByAccountNumberAndDateRange() {

        Transaction transaction1 = new Transaction(1L, ACC_NUM, BigDecimal.valueOf(100), LocalDate.of(2024, 1, 10));
        Transaction transaction2 = new Transaction(2L, ACC_NUM, BigDecimal.valueOf(200), LocalDate.of(2024, 1, 15));
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        Page<Transaction> transactionPage = new PageImpl<>(transactions, PageRequest.of(PAGE, SIZE), transactions.size());

        Mockito.when(transactionRepository.findTransactionsByAccountAndDateRange(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.any(Pageable.class)))
                .thenReturn(transactionPage);

        TransactionResponseDTO response = transactionService.getTransactionsByAccountNumberAndDateRange(ACC_NUM, FROM_DATE, TO_DATE, PAGE, SIZE);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.getTransactionDTOList().size());
        Assertions.assertTrue(response.isLastPage());
    }

}
