package com.espertech.esper.support.client;

import com.espertech.esper.core.StatementLifecycleObserver;
import com.espertech.esper.core.StatementLifecycleEvent;

import java.util.List;
import java.util.ArrayList;

public class SupportStmtLifecycleObserver implements StatementLifecycleObserver
{
    private List<StatementLifecycleEvent> events = new ArrayList<StatementLifecycleEvent>();
    private Object[] lastContext;

    public void observe(StatementLifecycleEvent event)
    {
        events.add(event);
        lastContext = event.getParams();
    }

    public Object[] getLastContext()
    {
        return lastContext;
    }

    public List<StatementLifecycleEvent> getEvents()
    {
        return events;
    }

    public String getEventsAsString()
    {
        String result = "";
        for (StatementLifecycleEvent event : events) {
            result += event.getEventType().toString() + ";";
        }
        return result;
    }

    public void flush()
    {
        events.clear();
    }
}