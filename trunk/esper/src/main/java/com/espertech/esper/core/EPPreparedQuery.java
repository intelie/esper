package com.espertech.esper.core;

import com.espertech.esper.event.EventType;

public interface EPPreparedQuery
{
    public EPQueryResult execute();
    public EventType getEventType();
}
