package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventBean;

public class RevisionStateDeclared
{
    private long revisionNumber;
    private EventBean fullEventUnderlying;
    private RevisionBeanHolder[] holders;
    private RevisionEventBeanDeclared lastEvent;

    public RevisionStateDeclared(EventBean fullEventUnderlying, RevisionBeanHolder[] holders, RevisionEventBeanDeclared lastEvent)
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

    public RevisionEventBeanDeclared getLastEvent()
    {
        return lastEvent;
    }

    public void setLastEvent(RevisionEventBeanDeclared lastEvent)
    {
        this.lastEvent = lastEvent;
    }
}
