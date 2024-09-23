package com.coral.event;

import lombok.Getter;

@Getter
public class StatementRequestEvent {

    private final Long requestId;

    public StatementRequestEvent(Long requestId) {
        this.requestId = requestId;
    }
}
