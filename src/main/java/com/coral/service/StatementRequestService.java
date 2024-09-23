package com.coral.service;

import com.coral.dto.StatementRequestDTO;
import com.coral.entity.StatementRequest;

public interface StatementRequestService {

    StatementRequest saveStatementRequest(StatementRequestDTO statementRequestDto);

    void processStatementRequestEvent(Long id);
}
