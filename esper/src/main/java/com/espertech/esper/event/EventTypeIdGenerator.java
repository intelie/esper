package com.espertech.esper.event;

public interface EventTypeIdGenerator {
    public int getTypeId(String eventTypeName);
}
