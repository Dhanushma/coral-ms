package com.coral.controller;

import com.coral.dto.TransactionDTO;
import com.coral.service.StatementProcessorService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
public class TestController {

    @Autowired
    StatementProcessorService statementGeneratorService;

    @GetMapping("/transaction-bank")
    public ResponseEntity<List<TransactionDTO>> getTransactionsForAcc(@RequestParam @NotBlank String accountNumber,
                                                      @RequestParam @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String fromDate,
                                                      @RequestParam @NotBlank @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String toDate) {

    return new ResponseEntity<>(statementGeneratorService.generateAccountStatement(accountNumber, fromDate, toDate), HttpStatus.OK);
    }

}
