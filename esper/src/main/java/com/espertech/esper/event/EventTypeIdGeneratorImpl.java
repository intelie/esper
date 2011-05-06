package com.espertech.esper.event;

import java.util.concurrent.atomic.AtomicInteger;

public class EventTypeIdGeneratorImpl implements EventTypeIdGenerator {

    private final AtomicInteger currentEventTypeId = new AtomicInteger();

    public int nextId(String typeName) {
        return currentEventTypeId.incrementAndGet();
    }
}
