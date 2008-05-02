package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;

public class RevisionState
{
    private long revisionNumber;
    private EventBean fullEvent;
    private RevisionBeanHolder[] holders;
    private RevisionEventBean lastEvent;

    public RevisionState(EventBean fullEvent, RevisionBeanHolder[] holders, RevisionEventBean lastEvent)
    {
        this.fullEvent = fullEvent;
        this.holders = holders;
        this.lastEvent = lastEvent;
    }

    public long getRevisionNumber()
    {
        return revisionNumber;
    }

    public long incRevisionNumber()
    {
        return ++revisionNumber;
    }

    public EventBean getFullEvent()
    {
        return fullEvent;
    }

    public void setFullEvent(EventBean fullEvent)
    {
        this.fullEvent = fullEvent;
    }

    public RevisionBeanHolder[] getHolders()
    {
        return holders;
    }

    public void setHolders(RevisionBeanHolder[] holders)
    {
        this.holders = holders;
    }

    public RevisionEventBean getLastEvent()
    {
        return lastEvent;
    }

    public void setLastEvent(RevisionEventBean lastEvent)
    {
        this.lastEvent = lastEvent;
    }
}
