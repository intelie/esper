package net.esper.support.filter;

import net.esper.filter.EventEvaluator;
import net.esper.filter.FilterCallback;
import net.esper.event.EventBean;

import java.util.List;

public class SupportEventEvaluator implements EventEvaluator
{
    private int countInvoked;
    private EventBean lastEvent;
    private List<FilterCallback> lastMatches;

    public void matchEvent(EventBean event, List<FilterCallback> matches)
    {
        countInvoked++;
        lastEvent = event;
        lastMatches = matches;
    }

    public EventBean getLastEvent()
    {
        return lastEvent;
    }

    public List<FilterCallback> getLastMatches()
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

    public void setLastMatches(List<FilterCallback> lastMatches)
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
