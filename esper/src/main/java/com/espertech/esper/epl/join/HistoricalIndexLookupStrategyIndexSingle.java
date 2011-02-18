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
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Iterator;
import java.util.Set;

/**
 * Index lookup strategy into a poll-based cache result.
 */
public class HistoricalIndexLookupStrategyIndexSingle implements HistoricalIndexLookupStrategy
{
    private final EventPropertyGetter propertyGetter;

    /**
     * Ctor.
     * @param eventType - event type to expect for lookup
     */
    public HistoricalIndexLookupStrategyIndexSingle(EventType eventType, String property)
    {
        propertyGetter = EventBeanUtility.getAssertPropertyGetter(eventType, property);
    }

    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable indexTable)
    {
        // The table may not be indexed as the cache may not actively cache, in which case indexing doesn't makes sense
        if (indexTable instanceof PropertyIndexedEventTableSingle)
        {
            PropertyIndexedEventTableSingle index = (PropertyIndexedEventTableSingle) indexTable;
            Object key = getKey(lookupEvent);

            Set<EventBean> events = index.lookup(key);
            if (events != null)
            {
                return events.iterator();
            }
            return null;
        }

        return indexTable.iterator();
    }

    private Object getKey(EventBean event)
    {
        return propertyGetter.get(event);
    }
}
