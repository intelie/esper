package net.esper.core;

import net.esper.event.EventBean;

public interface InternalEventRouter
{
    public void route(EventBean event);
}
