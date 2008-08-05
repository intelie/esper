package com.espertech.esper.epl.join;

import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.event.EventBean;

import java.util.Iterator;

/**
 * Strategy for use in poll-based joins to reduce a cached result set (represented by {@link EventTable}), in
 * which the cache result set may have been indexed, to fewer rows following the join-criteria in a where clause.
 */
public interface HistoricalIndexLookupStrategy
{
    /**
     * Look up into the index, potentially using some of the properties in the lookup event,
     * returning a partial or full result in respect to the index.
     * @param lookupEvent provides properties to use as key values for indexes
     * @param index is the table providing the cache result set, potentially indexed by index fields
     * @return full set or partial index iterator
     */
    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable index);
}