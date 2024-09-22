package com.coral.service;

import com.coral.dto.StatementRequestDTO;
import com.coral.entity.StatementRequest;
import com.coral.repository.StatementRequestRepository;
import com.coral.utils.StatementRequestConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatementRequestServiceImpl implements StatementRequestService {

    @Autowired
    private StatementRequestRepository statementRepository;

    @Override
    public void saveStatementRequest(StatementRequestDTO statementRequestDto) {
        StatementRequest statementRequest = statementRequestDto.toStatementRequest();
        statementRequest.setStatus(StatementRequestConstants.SCHEDULED);
        try {
            statementRepository.save(statementRequest);
        } catch (Exception e) {
            log.error("Error occurred while saving StatementRequest {}", e.getMessage());
            throw new RuntimeException("Failed to save StatementRequest", e);
        }
    }
}
