package com.espertech.esper.support.filter;

import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.client.EventBean;

public class SupportFilterHandle implements FilterHandleCallback
{
    private int countInvoked;
    private EventBean lastEvent;

    public void matchFound(EventBean event)
    {
        countInvoked++;
        lastEvent = event;
    }

    public boolean isSubSelect()
    {
        return false;
    }

    public int getCountInvoked()
    {
        return countInvoked;
    }

    public EventBean getLastEvent()
    {
        return lastEvent;
    }

    public void setCountInvoked(int countInvoked)
    {
        this.countInvoked = countInvoked;
    }

    public void setLastEvent(EventBean lastEvent)
    {
        this.lastEvent = lastEvent;
    }

    public int getAndResetCountInvoked()
    {
        int count = countInvoked;
        countInvoked = 0;
        return count;
    }

    public String getStatementId()
    {
        return "";
    }
}
