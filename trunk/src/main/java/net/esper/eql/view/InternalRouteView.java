package net.esper.eql.view;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.core.InternalEventRouter;

import java.util.Iterator;

public class InternalRouteView extends ViewSupport
{
    // Do we route the insert stream (new) events, or the remove stream (old) events
    private final boolean isIStream;
    private final InternalEventRouter internalEventRouter;

    public InternalRouteView(boolean isIStream, InternalEventRouter internalEventRouter)
    {
        this.isIStream = isIStream;
        this.internalEventRouter = internalEventRouter;
    }

    public String attachesTo(Viewable parentViewable)
    {
        return null;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((newData != null) && (isIStream))
        {
            route(newData);
        }

        if ((oldData != null) && (!isIStream))
        {
            route(oldData);
        }

        this.updateChildren(newData, oldData);
    }

    private void route(EventBean[] events)
    {
        for (int i = 0; i < events.length; i++)
        {
            internalEventRouter.route(events[i]);
        }
    }

    public EventType getEventType()
    {
        return parent.getEventType();
    }

    public Iterator<EventBean> iterator()
    {
        return parent.iterator();
    }
}
