/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.exec;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategy;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategyFactory;
import com.espertech.esper.epl.join.plan.RangeKeyDesc;
import com.espertech.esper.epl.join.rep.Cursor;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Set;

/**
 * Lookup on an index that is a sorted index on a single property queried as a range.
 * <p>
 * Use the composite strategy if supporting multiple ranges or if range is in combination with unique key.
 */
public class SortedTableLookupStrategy implements JoinExecTableLookupStrategy
{
    private final EventType eventType;
    private final RangeKeyDesc rangeKeyPair;
    private final PropertySortedEventTable index;
    private final SortedAccessStrategy strategy;

    /**
     * Ctor.
     * @param eventType - event type to expect for lookup
     * @param index - index to look up in
     */
    public SortedTableLookupStrategy(EventType eventType, RangeKeyDesc rangeKeyPair, PropertySortedEventTable index)
    {
        this.eventType = eventType;
        this.rangeKeyPair = rangeKeyPair;
        this.index = index;
        this.strategy = SortedAccessStrategyFactory.make(eventType, rangeKeyPair);
    }

    /**
     * Returns event type of the lookup event.
     * @return event type of the lookup event
     */
    public EventType getEventType()
    {
        return eventType;
    }

    /**
     * Returns index to look up in.
     * @return index to use
     */
    public PropertySortedEventTable getIndex()
    {
        return index;
    }

    public Set<EventBean> lookup(EventBean event, Cursor cursor, ExprEvaluatorContext exprEvaluatorContext)
    {
        return strategy.lookup(event, index);
    }

    public String toString()
    {
        return "SortedTableLookupStrategy indexProps=" + rangeKeyPair +
                " index=(" + index + ')';
    }
}
