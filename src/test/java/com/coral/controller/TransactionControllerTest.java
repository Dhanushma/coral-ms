package com.coral.controller;

import com.coral.dto.TransactionDTO;
import com.coral.dto.TransactionResponseDTO;
import com.coral.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private final String ACC_NUM = "1234567890";
    private final LocalDate FROM_DATE = LocalDate.of(2024, 1, 1);
    private final LocalDate TO_DATE = LocalDate.of(2024, 2, 1);

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(transactionController).build();
    }

    @Test
    public void testGetPaginatedTransactions_Success() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(1L, BigDecimal.valueOf(100), LocalDate.of(2024, 1, 10));
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(List.of(transactionDTO), true);

        Mockito.when(transactionService.getTransactionsByAccountNumberAndDateRange(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .param("accountNumber", ACC_NUM)
                        .param("fromDate", FROM_DATE.toString())
                        .param("toDate", TO_DATE.toString())
                        .param("size", "200")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionDTOList").isArray())
                .andExpect(jsonPath("$.transactionDTOList[0].transactionId").value(1L))
                .andExpect(jsonPath("$.transactionDTOList[0].transactionAmount").value(100.00))
                .andExpect(jsonPath("$.lastPage").value(true));
    }

    @Test
    public void testGetPaginatedTransactions_NoTransactions() throws Exception {
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(List.of(), true);

        Mockito.when(transactionService.getTransactionsByAccountNumberAndDateRange(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .param("accountNumber", ACC_NUM)
                        .param("fromDate", FROM_DATE.toString())
                        .param("toDate", TO_DATE.toString())
                        .param("size", "200")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionDTOList").isEmpty())
                .andExpect(jsonPath("$.lastPage").value(true));
    }

    @Test
    public void testGetPaginatedTransactions_InvalidDate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .param("accountNumber", ACC_NUM)
                        .param("fromDate", "invalid-date")
                        .param("toDate", "another-invalid-date")
                        .param("size", "200")
                        .param("page", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPaginatedTransactions_DefaultSizeAndPage() throws Exception {
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 2, 1);
        TransactionDTO transactionDTO = new TransactionDTO(1L, BigDecimal.valueOf(100), LocalDate.of(2024, 1, 10));
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(Arrays.asList(transactionDTO), true);

        Mockito.when(transactionService.getTransactionsByAccountNumberAndDateRange(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions")
                        .param("accountNumber", ACC_NUM)
                        .param("fromDate", fromDate.toString())
                        .param("toDate", toDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionDTOList").isArray())
                .andExpect(jsonPath("$.transactionDTOList[0].transactionId").value(1L))
                .andExpect(jsonPath("$.lastPage").value(true));
    }
}
