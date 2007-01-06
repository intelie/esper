package net.esper.support.filter;

import net.esper.filter.EventEvaluator;
import net.esper.filter.FilterHandle;
import net.esper.event.EventBean;

import java.util.List;
import java.util.Collection;

public class SupportEventEvaluator implements EventEvaluator
{
    private int countInvoked;
    private EventBean lastEvent;
    private Collection<FilterHandle> lastMatches;

    public void matchEvent(EventBean event, Collection<FilterHandle> matches)
    {
        countInvoked++;
        lastEvent = event;
        lastMatches = matches;
    }

    public EventBean getLastEvent()
    {
        return lastEvent;
    }

    public Collection<FilterHandle> getLastMatches()
    {
        return lastMatches;
    }

    public void setCountInvoked(int countInvoked)
    {
        this.countInvoked = countInvoked;
    }

    public void setLastEvent(EventBean lastEvent)
    {
        this.lastEvent = lastEvent;
    }

    public void setLastMatches(List<FilterHandle> lastMatches)
    {
        this.lastMatches = lastMatches;
    }

    public int getAndResetCountInvoked()
    {
        int count = countInvoked;
        countInvoked = 0;
        return count;
    }
}
