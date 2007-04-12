/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.exec;

import net.esper.event.EventBean;
import net.esper.util.IndentWriter;

import java.util.Set;
import java.util.List;

/**
 * Execution node for lookup in a table.
 */
public class TableLookupExecNode extends ExecNode
{
    private int indexedStream;
    private TableLookupStrategy lookupStrategy;

    /**
     * Ctor.
     * @param indexedStream - stream indexed for lookup
     * @param lookupStrategy - strategy to use for lookup (full table/indexed)
     */
    public TableLookupExecNode(int indexedStream, TableLookupStrategy lookupStrategy)
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

        if (joinedEvents == null)
        {
            return;
        }

        // Create result row for each found event
        for (EventBean joinedEvent : joinedEvents)
        {
            EventBean[] events = new EventBean[prefillPath.length];
            System.arraycopy(prefillPath, 0, events, 0, events.length);
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
        writer.println("TableLookupExecNode indexedStream=" + indexedStream + " lookup=" + lookupStrategy);
    }
}