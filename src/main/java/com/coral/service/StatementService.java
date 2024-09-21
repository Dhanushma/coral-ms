package com.coral.service;

import com.coral.dto.StatementRequestDto;

public interface StatementService {

    void generateStatement(StatementRequestDto statementRequestDto);
}
