package com.coral.service;

import com.coral.repository.StatementRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatementServiceTest {

    @InjectMocks
    private StatementServiceImpl statementService;

    @Mock
    private StatementRepository statementRepository;

}

