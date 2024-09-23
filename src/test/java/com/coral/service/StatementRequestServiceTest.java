package com.coral.service;

import com.coral.dto.StatementRequestDTO;
import com.coral.dto.TransactionDTO;
import com.coral.entity.StatementRequest;
import com.coral.repository.StatementRequestRepository;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StatementRequestServiceTest {

    @InjectMocks
    private StatementRequestServiceImpl statementRequestService;

    @Mock
    private StatementRequestRepository statementRequestRepository;

    @Mock
    private EmailServiceImpl emailService;

    @Mock
    private StatementProcessorService statementProcessorService;

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

    @Test
    public void testProcessStatementRequestEventSuccess() {
        StatementRequest statementRequest = StatementRequest.builder()
                .accountNumber(TEST_ACC).id(1L).status("Scheduled").fromDate(formatDateString(FROM_DATE)).toDate(formatDateString(TO_DATE)).build();
        Mockito.when(statementRequestRepository.findById(1L)).thenReturn(Optional.of(statementRequest));
        Mockito.when(statementProcessorService.generateAccountStatement(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Single.just(List.of(new TransactionDTO(1L, BigDecimal.valueOf(100.00), LocalDate.now()))));

        StatementRequest StatementRequestEntity = StatementRequest.builder().status("Completed").build();
        Mockito.lenient().when(statementRequestRepository.save(Mockito.any(StatementRequest.class))).thenReturn(StatementRequestEntity);
        statementRequestService.processStatementRequestEvent(1L);

        Assertions.assertEquals("Completed", StatementRequestEntity.getStatus());
    }

    @Test
    public void testProcessStatementRequestEventError() {
        StatementRequest statementRequest = StatementRequest.builder()
                .accountNumber(TEST_ACC).id(1L).status("Scheduled").fromDate(formatDateString(FROM_DATE)).toDate(formatDateString(TO_DATE)).build();
        Mockito.when(statementRequestRepository.findById(1L)).thenReturn(Optional.of(statementRequest));
        Mockito.when(statementProcessorService.generateAccountStatement(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Single.error(new RuntimeException("Statement generation failed")));
        StatementRequest failedStatementRequestEntity = StatementRequest.builder().status("failed").build();
        Mockito.lenient().when(statementRequestRepository.save(Mockito.any(StatementRequest.class))).thenReturn(failedStatementRequestEntity);
        statementRequestService.processStatementRequestEvent(1L);
        Mockito.verify(emailService, Mockito.never()).generateEmailStatement(Mockito.anyList(), Mockito.anyString());
    }

    private LocalDate formatDateString(String date) {
        return LocalDate.parse(date);
    }

}

