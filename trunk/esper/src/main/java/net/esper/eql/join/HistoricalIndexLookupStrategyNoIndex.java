package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.eql.join.table.EventTable;

import java.util.Iterator;
import java.util.Set;
import java.util.Collection;

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
