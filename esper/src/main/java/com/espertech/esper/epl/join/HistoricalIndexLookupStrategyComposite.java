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
import com.espertech.esper.epl.join.plan.RangeKeyDesc;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;
import com.espertech.esper.epl.join.table.comp.InnerIndexQuery;
import com.espertech.esper.epl.join.table.comp.InnerIndexQueryFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Index lookup strategy into a poll-based cache result.
 */
public class HistoricalIndexLookupStrategyComposite implements HistoricalIndexLookupStrategy
{
    private final InnerIndexQuery chain;

    public HistoricalIndexLookupStrategyComposite(EventType eventType, String[] keyPropertiesJoin, Class[] keyCoercionTypes, List<RangeKeyDesc> rangeKeyPairs, Class[] rangeCoercionTypes) {
        chain = InnerIndexQueryFactory.make(eventType, keyPropertiesJoin, keyCoercionTypes, rangeKeyPairs, rangeCoercionTypes);
    }

    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable indexTable)
    {
        // The table may not be indexed as the cache may not actively cache, in which case indexing doesn't makes sense
        if (indexTable instanceof PropertyCompositeEventTable)
        {
            PropertyCompositeEventTable table = (PropertyCompositeEventTable) indexTable;
            Map<Object, Object> index = table.getIndex();

            Set<EventBean> events = chain.get(lookupEvent, index);
            if (events != null)
            {
                return events.iterator();
            }
            return null;
        }

        return indexTable.iterator();
    }
}
