package com.coral.controller;

import com.coral.dto.StatementRequestDTO;
import com.coral.entity.StatementRequest;
import com.coral.service.StatementRequestService;
import com.coral.utils.StatementRequestConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class StatementControllerTest {

    @Mock
    private StatementRequestService statementService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private StatementController statementController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private final String FROM_DATE = "2024-01-01";
    private final String TO_DATE = "2024-02-01";
    private final String TEST_ACC = "1234567890";
    private final String EMAIL = "test@gmail.com";
    private final String PATH = "/coral-ms/api/statements";


    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(statementController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    public void testGenerateStatementSuccess() throws Exception {
        StatementRequestDTO statementRequestDto = new StatementRequestDTO();
        statementRequestDto.setAccountNumber(TEST_ACC);
        statementRequestDto.setFromDate(FROM_DATE);
        statementRequestDto.setToDate(TO_DATE);
        statementRequestDto.setEmailId(EMAIL);

        StatementRequest statementRequest = StatementRequest.builder().id(1L).build();
        Mockito.when(statementService.saveStatementRequest(statementRequestDto)).thenReturn(statementRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statementRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(StatementRequestConstants.STATEMENT_REQ_SUBMITTED));

        Mockito.verify(statementService, Mockito.times(1)).saveStatementRequest(statementRequestDto);
    }

    @Test
    public void testGenerateStatementValidationError() throws Exception {
        StatementRequestDTO statementRequestDto = new StatementRequestDTO();
        statementRequestDto.setFromDate(FROM_DATE);
        statementRequestDto.setToDate(TO_DATE);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statementRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
