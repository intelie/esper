/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.lookup;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategy;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategyFactory;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.Collection;

/**
 * Index lookup strategy for subqueries.
 */
public class SortedTableLookupStrategy implements TableLookupStrategy
{
    private final SubqueryRangeKeyDesc rangeKey;

    /**
     * Index to look up in.
     */
    protected final PropertySortedEventTable index;

    protected final SortedAccessStrategy strategy;

    /**
     * Ctor.
     * @param eventTypes is the event types per stream
     * @param index is the table carrying the data to lookup into
     */
    public SortedTableLookupStrategy(EventType[] eventTypes, SubqueryRangeKeyDesc rangeKey, PropertySortedEventTable index)
    {
        this.rangeKey = rangeKey;
        this.index = index;
        this.strategy = SortedAccessStrategyFactory.make(eventTypes, rangeKey);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream)
    {
        return strategy.lookup(eventsPerStream, index);
    }

    public String toString()
    {
        return "SortedTableLookupStrategy range=" + rangeKey +
                " index=(" + index + ')';
    }
}
