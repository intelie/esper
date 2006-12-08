package net.esper.eql.join.exec;

import net.esper.event.EventBean;
import net.esper.util.IndentWriter;

import java.util.Set;
import java.util.List;

/**
 * Execution node for lookup in a table for outer joins. This execution node thus generates
 * rows even if no joined events could be found, the joined table events are set to null if no
 * joined events are found.
 */
public class TableOuterLookupExecNode extends ExecNode
{
    private int indexedStream;
    private TableLookupStrategy lookupStrategy;

    /**
     * Ctor.
     * @param indexedStream - stream indexed for lookup
     * @param lookupStrategy - strategy to use for lookup (full table/indexed)
     */
    public TableOuterLookupExecNode(int indexedStream, TableLookupStrategy lookupStrategy)
    {
        this.indexedStream = indexedStream;
        this.lookupStrategy = lookupStrategy;
    }

    /**
     * Returns strategy for lookup.
     * @return lookup strategy
     */
    public TableLookupStrategy getLookupStrategy()
    {
        return lookupStrategy;
    }

    public void process(EventBean lookupEvent, EventBean[] prefillPath, List<EventBean[]> result)
    {
        // Lookup events
        Set<EventBean> joinedEvents = lookupStrategy.lookup(lookupEvent);

        // If no events are found, since this is an outer join, create a result row leaving the
        // joined event as null.
        if ((joinedEvents == null) || (joinedEvents.size() == 0))
        {
            EventBean[] events = new EventBean[prefillPath.length];
            for (int i = 0; i < events.length; i++)
            {
                events[i] = prefillPath[i];
            }
            result.add(events);

            return;
        }

        // Create result row for each found event
        for (EventBean joinedEvent : joinedEvents)
        {
            EventBean[] events = new EventBean[prefillPath.length];
            for (int i = 0; i < events.length; i++)
            {
                events[i] = prefillPath[i];
            }
            events[indexedStream] = joinedEvent;
            result.add(events);
        }
    }

    /**
     * Returns target stream for lookup.
     * @return indexed stream
     */
    public int getIndexedStream()
    {
        return indexedStream;
    }

    public void print(IndentWriter writer)
    {
        writer.println("TableOuterLookupExecNode indexedStream=" + indexedStream + " lookup=" + lookupStrategy);
    }
}