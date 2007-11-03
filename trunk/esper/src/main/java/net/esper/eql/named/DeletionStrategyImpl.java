package net.esper.eql.named;

import net.esper.event.EventBean;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.join.table.EventTable;
import net.esper.view.View;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class DeletionStrategyImpl implements DeletionStrategy
{
    private final EventTable table;
    private final ExprNode joinExpr;
    private final View removeStreamView;
    private final EventBean[] eventsPerStream;

    public DeletionStrategyImpl(EventTable table, ExprNode joinExpr, View removeStreamView)
    {
        this.table = table;
        this.joinExpr = joinExpr;
        this.removeStreamView = removeStreamView;
        this.eventsPerStream = new EventBean[2];
    }

    public void matchedDelete(EventBean[] newData)
    {
        Set<EventBean> removeEvents = new HashSet<EventBean>();
        for (int i = 0; i < newData.length; i++)
        {
            eventsPerStream[1] = newData[i];    // Stream 1 events are the originating events (on-delete events)

            Iterator<EventBean> eventsIt = table.iterator();
            for (;eventsIt.hasNext();)
            {
                eventsPerStream[0] = eventsIt.next();

                Boolean result = (Boolean) joinExpr.evaluate(eventsPerStream, true);
                if (result != null)
                {
                    if (result)
                    {
                        removeEvents.add(eventsPerStream[0]);
                    }
                }
            }
        }

        // if the are events to remove, send a remove stream if there are views
        if (removeEvents.size() > 0)
        {
            EventBean[] oldEvents = removeEvents.toArray(new EventBean[0]);
            removeStreamView.update(null, oldEvents);
        }
    }
}
