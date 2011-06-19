package com.espertech.esper.event;

import com.espertech.esper.event.bean.BeanEventType;

import java.util.concurrent.atomic.AtomicInteger;

public class EventTypeIdGeneratorImpl implements EventTypeIdGenerator {

    private final AtomicInteger currentEventTypeId = new AtomicInteger();

    public EventTypeIdGeneratorImpl() {
    }

    public int getTypeId(String typeName) {
        return currentEventTypeId.incrementAndGet();
    }

    public void assignedType(String name, BeanEventType eventType) {
        // no op required
    }
}
