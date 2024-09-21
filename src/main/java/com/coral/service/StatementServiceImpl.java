package com.coral.service;

import com.coral.dto.StatementRequestDto;
import com.coral.entity.StatementRequest;
import com.coral.repository.StatementRepository;
import com.coral.utils.StatementConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementServiceImpl implements StatementService {

    @Autowired
    private StatementRepository statementRepository;

    @Override
    public void generateStatement(StatementRequestDto statementRequestDto) {

        StatementRequest statementRequest = statementRequestDto.toStatementRequest();
        statementRequest.setStatus(StatementConstants.SCHEDULED);
        statementRepository.save(statementRequest);
    }
}
