package net.esper.eql.named;

import net.esper.event.EventBean;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.join.table.EventTable;
import net.esper.view.View;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Determine events to be deleted from a named window using the where-clause and full table scan.
 */
public class DeletionStrategyTableScan implements DeletionStrategy
{
    private final ExprNode joinExpr;
    private final EventBean[] eventsPerStream;
    private final Iterable<EventBean> iterableNamedWindow;

    /**
     * Ctor.
     * @param joinExpr is the where clause
     * @param iterable is the named window's data window iterator
     */
    public DeletionStrategyTableScan(ExprNode joinExpr, Iterable<EventBean> iterable)
    {
        this.joinExpr = joinExpr;
        this.eventsPerStream = new EventBean[2];
        this.iterableNamedWindow = iterable;
    }

    public EventBean[] determineRemoveStream(EventBean[] newData)
    {
        Set<EventBean> removeEvents = null;

        Iterator<EventBean> eventsIt = iterableNamedWindow.iterator();
        for (;eventsIt.hasNext();)
        {
            eventsPerStream[0] = eventsIt.next();   // next named window event

            for (EventBean aNewData : newData)
            {
                eventsPerStream[1] = aNewData;    // Stream 1 events are the originating events (on-delete events)

                Boolean result = (Boolean) joinExpr.evaluate(eventsPerStream, true);
                if (result != null)
                {
                    if (result)
                    {
                        if (removeEvents == null)
                        {
                            removeEvents = new LinkedHashSet<EventBean>();
                        }
                        removeEvents.add(eventsPerStream[0]);
                    }
                }
            }
        }

        if (removeEvents == null)
        {
            return null;
        }

        return removeEvents.toArray(new EventBean[0]);
    }
}
