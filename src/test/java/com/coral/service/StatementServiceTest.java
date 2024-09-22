package com.coral.service;

import com.coral.dto.StatementRequestDTO;
import com.coral.entity.StatementRequest;
import com.coral.repository.StatementRequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatementServiceTest {

    @InjectMocks
    private StatementRequestServiceImpl statementRequestService;

    @Mock
    private StatementRequestRepository statementRequestRepository;

    private final String FROM_DATE = "2024-01-01";
    private final String TO_DATE = "2024-02-01";
    private final String TEST_ACC = "1234567890";

    @Test
    public void testSaveStatementSuccessRequest() {
        StatementRequestDTO requestDTO = new StatementRequestDTO();
        requestDTO.setAccountNumber(TEST_ACC);
        requestDTO.setFromDate(FROM_DATE);
        requestDTO.setToDate(TO_DATE);
        statementRequestService.saveStatementRequest(requestDTO);
        Mockito.verify(statementRequestRepository, Mockito.times(1)).save(Mockito.any(StatementRequest.class));
    }

    @Test
    public void testSaveStatementRequestException() {
        StatementRequestDTO requestDTO = new StatementRequestDTO();
        requestDTO.setAccountNumber(TEST_ACC);
        requestDTO.setFromDate(FROM_DATE);
        requestDTO.setToDate(TO_DATE);
        Mockito.doThrow(new RuntimeException("Database error")).when(statementRequestRepository).save(Mockito.any(StatementRequest.class));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            statementRequestService.saveStatementRequest(requestDTO);
        });
        Assertions.assertEquals("Failed to save StatementRequest", thrown.getMessage());
    }
}

