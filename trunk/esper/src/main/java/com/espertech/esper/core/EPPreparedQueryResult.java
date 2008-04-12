package com.espertech.esper.core;

import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;

public class EPPreparedQueryResult
{
    private final EventType eventType;
    private final EventBean[] result;

    public EPPreparedQueryResult(EventType eventType, EventBean[] result)
    {
        this.eventType = eventType;
        this.result = result;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public EventBean[] getResult()
    {
        return result;
    }
}
