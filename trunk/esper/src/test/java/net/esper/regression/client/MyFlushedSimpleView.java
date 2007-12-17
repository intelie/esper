package net.esper.regression.client;

import net.esper.core.StatementContext;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.StatementStopCallback;
import net.esper.view.View;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyFlushedSimpleView extends ViewSupport implements StatementStopCallback
{
    private List<EventBean> events;
    private EventType eventType;

    public MyFlushedSimpleView(StatementContext statementContext)
    {
        statementContext.getStatementStopService().addSubscriber(this);
        events = new ArrayList<EventBean>();
    }

    public void statementStopped()
    {
        this.updateChildren(events.toArray(new EventBean[0]), null);
        events = new ArrayList<EventBean>();
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        eventType = parent.getEventType();
    }

    public View cloneView(StatementContext statementContext)
    {
        return new MyFlushedSimpleView(statementContext);
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                events.add(newData[0]);
            }
        }
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        return events.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName();
    }
}
