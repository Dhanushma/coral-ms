package com.coral.service;

import com.coral.dto.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    @Override
    public void generateEmailStatement(List<TransactionDTO> transactions, String emailId) {
        // invoke Email service
        // send email to customer
        log.info("Size of Transaction statement: {}", transactions.size());
        log.info("Email has been successfully sent to {}", emailId);
    }
}
