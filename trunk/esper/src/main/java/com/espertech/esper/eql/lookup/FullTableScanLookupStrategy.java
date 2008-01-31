package com.espertech.esper.eql.lookup;

import com.espertech.esper.eql.join.table.UnindexedEventTable;
import com.espertech.esper.event.EventBean;

import java.util.Set;

/**
 * Lookup on an unindexed table returning the full table as matching events.
 */
public class FullTableScanLookupStrategy implements TableLookupStrategy
{
    private UnindexedEventTable eventIndex;

    /**
     * Ctor.
     * @param eventIndex - table to use
     */
    public FullTableScanLookupStrategy(UnindexedEventTable eventIndex)
    {
        this.eventIndex = eventIndex;
    }

    public Set<EventBean> lookup(EventBean[] eventPerStream)
    {
        Set<EventBean> result = eventIndex.getEventSet();
        if (result.isEmpty())
        {
            return null;
        }
        return result;
    }

    /**
     * Returns the associated table.
     * @return table for lookup.
     */
    public UnindexedEventTable getEventIndex()
    {
        return eventIndex;
    }
}
