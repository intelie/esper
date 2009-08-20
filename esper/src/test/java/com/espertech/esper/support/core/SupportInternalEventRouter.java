package com.espertech.esper.support.core;

import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.client.EventBean;

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
