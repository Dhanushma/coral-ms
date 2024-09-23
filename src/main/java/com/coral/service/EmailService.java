package com.coral.service;

import com.coral.dto.TransactionDTO;

import java.util.List;

public interface EmailService {

    void generateEmailStatement(List<TransactionDTO> transactions, String emailId);
}
