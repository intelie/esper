package com.espertech.esper.epl.lookup;

import com.espertech.esper.event.EventBean;

import java.util.Set;
import java.util.HashSet;

/**
 * Implementation for a table lookup strategy that returns exactly one row
 * but leaves that row as an undefined value.
 */
public class TableLookupStrategyNullRow implements TableLookupStrategy
{
    private static Set<EventBean> singleNullRowEventSet = new HashSet<EventBean>();

    static
    {
        singleNullRowEventSet.add(null);
    }

    public Set<EventBean> lookup(EventBean[] events) {
        return singleNullRowEventSet;        
    }
}
