package com.espertech.esper.event;

public interface EventTypeIdGenerator {
    public int nextId(String eventTypeName);
}
