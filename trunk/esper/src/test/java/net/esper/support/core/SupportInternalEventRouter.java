package net.esper.support.core;

import net.esper.core.InternalEventRouter;
import net.esper.core.EPStatementHandle;
import net.esper.event.EventBean;

import java.util.List;
import java.util.LinkedList;

public class SupportInternalEventRouter implements InternalEventRouter
{
    private List<EventBean> routed  = new LinkedList<EventBean>();

    public void route(EventBean event, EPStatementHandle epStatementHandle)
    {
        routed.add(event);
    }

    public List<EventBean> getRouted()
    {
        return routed;
    }

    public void reset()
    {
        routed.clear();
    }
}
