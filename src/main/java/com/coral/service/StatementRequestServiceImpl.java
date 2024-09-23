package com.coral.service;

import com.coral.dto.StatementRequestDTO;
import com.coral.dto.TransactionDTO;
import com.coral.entity.StatementRequest;
import com.coral.repository.StatementRequestRepository;
import com.coral.utils.StatementRequestConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.coral.utils.StatementRequestConstants.*;

@Service
@Slf4j
public class StatementRequestServiceImpl implements StatementRequestService {

    @Autowired
    private StatementRequestRepository statementRequestRepository;

    @Override
    public StatementRequest saveStatementRequest(StatementRequestDTO statementRequestDto) {
        StatementRequest statementRequest = statementRequestDto.toStatementRequest();
        statementRequest.setStatus(StatementRequestConstants.SCHEDULED);
        try {
            return statementRequestRepository.save(statementRequest);
        } catch (Exception e) {
            log.error("Error occurred while saving StatementRequest {}", e.getMessage());
            throw new RuntimeException("Failed to save StatementRequest", e);
        }
    }

    @Override
    public void processStatementRequestEvent(Long id) {
        Optional<StatementRequest> optionalStatementRequest = statementRequestRepository.findById(id);
        if (optionalStatementRequest.isPresent() && SCHEDULED.equals(optionalStatementRequest.get().getStatus())) {
            StatementRequest statementRequest = optionalStatementRequest.get();
            statementRequest.setStatus(PROCESSING);
            statementRequestRepository.save(statementRequest);

            log.info("call StatementProcessor service");
            List<TransactionDTO> transactionsList = generateStatementForRequest(statementRequest);

            log.info("Invoking Email Service to trigger email to customer");
            generateEmailStatement(transactionsList, statementRequest.getEmailId());

            statementRequest.setStatus(COMPLETED);
            statementRequestRepository.save(statementRequest);
        }
    }

    private List<TransactionDTO> generateStatementForRequest(StatementRequest statementRequest) {
        // add logic
        return new ArrayList<>();
    }

    private void generateEmailStatement(List<TransactionDTO> transactions, String emailId) {
        // send email to customer
        log.info("Email has been successfully sent");
    }


}
