package com.espertech.esper.event;

import java.util.concurrent.atomic.AtomicInteger;

public class EventTypeIdGeneratorImpl implements EventTypeIdGenerator {

    private final AtomicInteger currentEventTypeId = new AtomicInteger();

    public EventTypeIdGeneratorImpl() {
    }

    public int getTypeId(String typeName) {
        return currentEventTypeId.incrementAndGet();
    }
}
