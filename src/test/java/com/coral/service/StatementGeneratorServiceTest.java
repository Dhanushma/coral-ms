package com.coral.service;

import com.coral.dto.TransactionDTO;
import com.coral.dto.TransactionResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StatementGeneratorServiceTest {

    @InjectMocks
    private StatementProcessorService statementGeneratorService;

    @Mock
    private RestTemplate restTemplate;

    private final String ACCOUNT_NUMBER = "1234567890";
    private final String FROM_DATE = "2024-01-01";
    private final String TO_DATE = "2024-02-01";


    @Test
    void generateAccountStatement_shouldReturnListOfTransactions() {


        TransactionResponseDTO firstPageResponse = new TransactionResponseDTO();
        firstPageResponse.setTransactionDTOList(createMockTransactionList(5));
        firstPageResponse.setLastPage(false);

        TransactionResponseDTO secondPageResponse = new TransactionResponseDTO();
        secondPageResponse.setTransactionDTOList(createMockTransactionList(3));
        secondPageResponse.setLastPage(true);

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(TransactionResponseDTO.class)))
                .thenReturn(firstPageResponse)
                .thenReturn(secondPageResponse);

        List<TransactionDTO> transactions = statementGeneratorService.generateAccountStatement(ACCOUNT_NUMBER, FROM_DATE, TO_DATE);


        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(8, transactions.size());
        Mockito.verify(restTemplate, Mockito.times(2)).getForObject(Mockito.anyString(), Mockito.eq(TransactionResponseDTO.class));
    }

    private List<TransactionDTO> createMockTransactionList(int size) {
        List<TransactionDTO> transactions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            transactions.add(new TransactionDTO());
        }
        return transactions;
    }
}
