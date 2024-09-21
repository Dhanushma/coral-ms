package com.coral.controller;

import com.coral.dto.StatementRequestDto;
import com.coral.service.StatementService;
import com.coral.utils.StatementConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class StatementControllerTest {

    @Mock
    private StatementService statementService;

    @InjectMocks
    private StatementController statementController;



    private StatementRequestDto setUp() {
        StatementRequestDto statementRequestDto = new StatementRequestDto();
        statementRequestDto.setAccountNumber("Test_Acc");
        LocalDate fromDate = LocalDate.parse("2024-01-01");
        LocalDate toDate = LocalDate.parse("2024-02-01");
        statementRequestDto.setFromDate(fromDate);
        statementRequestDto.setToDate(toDate);
        return statementRequestDto;
    }

    @Test
    public void testGenerateStatement_Success() {
        StatementRequestDto requestDto = setUp();
        Mockito.doNothing().when(statementService).generateStatement(Mockito.any());
        ResponseEntity<String> response = statementController.generateStatement(requestDto);
        Mockito.verify(statementService).generateStatement(requestDto);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(StatementConstants.STATEMENT_REQ_SUBMITTED, response.getBody());
    }

    //TODO - failure test
    /*@Test
    public void testGenerateStatement_Failure() {
        StatementRequestDto requestDto = setUp();
        Mockito.doThrow(new RuntimeException("Error occurred while calling statement service"))
                .when(statementService).generateStatement(Mockito.any(StatementRequestDto.class));
        ResponseEntity<String> response = statementController.generateStatement(requestDto);
        Mockito.verify(statementService).generateStatement(requestDto);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }*/
}
