package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;

public class RevisionState
{
    private long revisionNumber;
    private EventBean fullEventUnderlying;
    private RevisionBeanHolder[] holders;
    private RevisionEventBean lastEvent;

    public RevisionState(EventBean fullEventUnderlying, RevisionBeanHolder[] holders, RevisionEventBean lastEvent)
    {
        this.fullEventUnderlying = fullEventUnderlying;
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

    public EventBean getFullEventUnderlying()
    {
        return fullEventUnderlying;
    }

    public void setFullEventUnderlying(EventBean fullEventUnderlying)
    {
        this.fullEventUnderlying = fullEventUnderlying;
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
