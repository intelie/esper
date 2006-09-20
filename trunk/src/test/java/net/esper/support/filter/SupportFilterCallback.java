package net.esper.support.filter;

import net.esper.filter.FilterCallback;
import net.esper.event.EventBean;

public class SupportFilterCallback implements FilterCallback
{
    private int countInvoked;
    private EventBean lastEvent;

    public void matchFound(EventBean event)
    {
        countInvoked++;
        lastEvent = event;
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
}
