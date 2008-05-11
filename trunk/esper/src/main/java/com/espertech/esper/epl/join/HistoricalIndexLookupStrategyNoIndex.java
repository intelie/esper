package com.espertech.esper.epl.join;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.epl.join.table.EventTable;

import java.util.Iterator;

/**
 * Full table scan strategy for a poll-based cache result.
 */
public class HistoricalIndexLookupStrategyNoIndex implements HistoricalIndexLookupStrategy
{
    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable index)
    {
        return index.iterator();
    }
}
