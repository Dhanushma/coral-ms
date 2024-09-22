package com.coral.controller;

import com.coral.dto.ErrorResponseDTO;
import com.coral.dto.TransactionResponseDTO;
import com.coral.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(
        name = "Transactions Rest APIs",
        description = "Rest APIs for Transactions"
)
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(
            summary = "Get Transactions",
            description = "Get Transactions for an account based on fromDate and ToDate and PageNumber"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request"
            )
    })
    @GetMapping("/transactions")
    public ResponseEntity<TransactionResponseDTO> getPaginatedTransactions(@RequestParam @NotBlank String accountNumber,
                                                                           @RequestParam @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String fromDate,
                                                                           @RequestParam @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String toDate,
                                                                           @RequestParam(name = "size", defaultValue = "200") int size,
                                                                           @RequestParam(name = "page", defaultValue = "0") int page) {
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);
        TransactionResponseDTO responseDTO = transactionService.getTransactionsByAccountNumberAndDateRange(accountNumber, from, to, page, size);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


}
