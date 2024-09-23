package com.coral.event;

import com.coral.service.StatementRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StatementRequestEventHandler {

    @Autowired
    private StatementRequestService statementRequestService;

    @EventListener
    public void handleStatementRequestEvent(StatementRequestEvent event) {
        log.info("Processing statement request event with requestId:{}", event.getRequestId());
        try {
            statementRequestService.processStatementRequestEvent(event.getRequestId());
        } catch (Exception e) {
            log.error("Error occurred while processing statement request event {}", e.getMessage());
            throw new RuntimeException("Error occurred while processing statement request", e);
        }
    }
}
