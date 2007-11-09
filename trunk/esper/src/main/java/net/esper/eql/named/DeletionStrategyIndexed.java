package net.esper.eql.named;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.lookup.TableLookupStrategy;
import net.esper.event.EventBean;

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class DeletionStrategyIndexed implements DeletionStrategy
{
    private final ExprNode joinExpr;
    private final EventBean[] eventsPerStream;
    private final TableLookupStrategy tableLookupStrategy;

    public DeletionStrategyIndexed(ExprNode joinExpr, TableLookupStrategy tableLookupStrategy)
    {
        this.joinExpr = joinExpr;
        this.eventsPerStream = new EventBean[2];
        this.tableLookupStrategy = tableLookupStrategy;
    }

    public EventBean[] determineRemoveStream(EventBean[] newData)
    {
        Set<EventBean> removeEvents = null;

        // For every new event (usually 1)
        for (EventBean newEvent : newData)
        {
            eventsPerStream[1] = newEvent;

            // use index to find match
            Set<EventBean> matches = tableLookupStrategy.lookup(eventsPerStream);
            if ((matches == null) || (matches.isEmpty()))
            {
                continue;
            }

            // evaluate expression
            Iterator<EventBean> eventsIt = matches.iterator();
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
        }

        if (removeEvents == null)
        {
            return null;
        }

        return removeEvents.toArray(new EventBean[0]);
    }
}
