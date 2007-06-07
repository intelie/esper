/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.exec;

import net.esper.event.EventBean;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.subquery.SubqueryTableLookupStrategy;

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

    public Set<EventBean> lookup(EventBean event)
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
