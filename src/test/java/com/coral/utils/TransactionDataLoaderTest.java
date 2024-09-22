package com.coral.utils;

import com.coral.entity.Transaction;
import com.coral.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class TransactionDataLoaderTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionDataLoader transactionDataLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun_ShouldLoadTransactionData() throws Exception {
        String accountNumber = "1234567890";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 1);

        transactionDataLoader.run();
        Mockito.verify(transactionRepository, Mockito.times(100)).save(Mockito.any(Transaction.class));
    }
}
