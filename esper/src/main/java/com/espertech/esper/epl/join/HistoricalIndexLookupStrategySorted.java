/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategy;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategyFactory;
import com.espertech.esper.epl.join.plan.QueryGraphValueRange;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Iterator;
import java.util.Set;

/**
 * Index lookup strategy into a poll-based cache result.
 */
public class HistoricalIndexLookupStrategySorted implements HistoricalIndexLookupStrategy
{
    private final SortedAccessStrategy strategy;

    /**
     * Ctor.
     * @param eventType - event type to expect for lookup
     */
    public HistoricalIndexLookupStrategySorted(EventType eventType, QueryGraphValueRange property)
    {
        strategy = SortedAccessStrategyFactory.make(eventType, property);
    }

    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable indexTable)
    {
        // The table may not be indexed as the cache may not actively cache, in which case indexing doesn't makes sense
        if (indexTable instanceof PropertySortedEventTable)
        {
            PropertySortedEventTable index = (PropertySortedEventTable) indexTable;
            Set<EventBean> events = strategy.lookup(lookupEvent, index);
            if (events != null)
            {
                return events.iterator();
            }
            return null;
        }

        return indexTable.iterator();
    }
}
