package com.coral.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StatementRequestEvent extends ApplicationEvent {

    private final Long requestId;

    public StatementRequestEvent(Object source, Long requestId) {
        super(source);
        this.requestId = requestId;
    }
}
