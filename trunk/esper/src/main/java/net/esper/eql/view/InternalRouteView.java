/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.view;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.core.InternalEventRouter;

import java.util.Iterator;

/**
 * View for internally routing events which is commenly the last step in execution of a statement
 * in which an insert-into clause has been specified.
 */
public class InternalRouteView extends ViewSupport
{
    // Do we route the insert stream (new) events, or the remove stream (old) events
    private final boolean isIStream;
    private final InternalEventRouter internalEventRouter;

    /**
     * Ctor.
     * @param isIStream true for insert stream, false for remove stream
     * @param internalEventRouter routes the events internally
     */
    public InternalRouteView(boolean isIStream, InternalEventRouter internalEventRouter)
    {
        this.isIStream = isIStream;
        this.internalEventRouter = internalEventRouter;
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