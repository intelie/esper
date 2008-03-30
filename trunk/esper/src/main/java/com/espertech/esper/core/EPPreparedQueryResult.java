package com.espertech.esper.core;

import com.espertech.esper.client.EPPreparedQuery;
import com.espertech.esper.client.EPQueryResult;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;

import java.util.List;

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
