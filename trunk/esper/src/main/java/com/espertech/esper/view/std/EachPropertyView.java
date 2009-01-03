package com.espertech.esper.view.std;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.SingleEventIterator;
import com.espertech.esper.collection.ArrayEventIterator;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.*;

import java.util.Iterator;

public class EachPropertyView extends ViewSupport implements CloneableView, DataWindowView
{
    private final ExprNode[] exprNodes;
    private final EventType selfEventType;
    private final EventPropertyGetter fragmentGetter;
    private final boolean isIndexed;

    private EventBean[] eventsPerStreamOld = new EventBean[1];
    private EventBean[] eventsPerStreamNew = new EventBean[1];
    private EventBean[] lastEvents;

    public EachPropertyView(ExprNode[] exprNodes, EventType selfEventType, EventPropertyGetter fragmentGetter, boolean isIndexed)
    {
        this.exprNodes = exprNodes;
        this.selfEventType = selfEventType;
        this.fragmentGetter = fragmentGetter;
        this.isIndexed = isIndexed;
    }

    public View cloneView(StatementContext context)
    {
        return new EachPropertyView(exprNodes, selfEventType, fragmentGetter, isIndexed);
    }

    public final EventType getEventType()
    {
        // The schema is the parent view's schema
        return parent.getEventType();
    }

    public ExprNode[] getExprNodes()
    {
        return exprNodes;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newData == null)
        {
            return;
        }

        if (newData.length == 1)
        {
            Object result = fragmentGetter.getFragment(newData[0]);
            if (result == null)
            {
                return;
            }

            if (isIndexed)
            {
                EventBean[] events = (EventBean[]) result;
                updateChildren(events, lastEvents);
                lastEvents = events;
            }
            else
            {
                EventBean event = (EventBean) result;
                eventsPerStreamNew[0] = event;
                if (eventsPerStreamOld[0] != null)
                {
                    updateChildren(eventsPerStreamNew, eventsPerStreamOld);
                }
                else
                {
                    updateChildren(eventsPerStreamNew, null);
                }
                eventsPerStreamOld[0] = event;
            }
        }
    }

    public final Iterator<EventBean> iterator()
    {
        if (isIndexed)
        {
            return new ArrayEventIterator(lastEvents);
        }
        else
        {
            return new ArrayEventIterator(eventsPerStreamOld);            
        }
    }

    public final String toString()
    {
        return this.getClass().getName();
    }
}
