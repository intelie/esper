package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.NullIterator;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.ViewSupport;

import java.util.Iterator;

/**
 * View for processing split-stream syntax.
 */
public class RouteResultView extends ViewSupport
{
    private final static NullIterator nullIterator = new NullIterator();
    private final EventType eventType;
    private RouteResultViewHandler handler;

    /**
     * Ctor.
     * @param isFirst true for the first-where clause, false for all where-clauses
     * @param eventType output type
     * @param epStatementHandle handle
     * @param internalEventRouter routining output events
     * @param processors processors for select clauses
     * @param whereClauses where expressions
     */
    public RouteResultView(boolean isFirst, EventType eventType, EPStatementHandle epStatementHandle, InternalEventRouter internalEventRouter, ResultSetProcessor[] processors, ExprNode[] whereClauses)
    {
        if (whereClauses.length != processors.length)
        {
            throw new IllegalArgumentException("Number of where-clauses and processors does not match");
        }

        this.eventType = eventType;
        if (isFirst)
        {
            handler = new RouteResultViewHandlerFirst(epStatementHandle, internalEventRouter, processors, whereClauses);
        }
        else
        {
            handler = new RouteResultViewHandlerAll(epStatementHandle, internalEventRouter, processors, whereClauses);
        }
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newData == null)
        {
            return;
        }

        for (EventBean bean : newData)
        {
            boolean isHandled = handler.handle(bean);

            if (!isHandled)
            {
                updateChildren(new EventBean[] {bean}, null);
            }
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return nullIterator;
    }
}
