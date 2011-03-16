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
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategy;
import com.espertech.esper.epl.join.exec.sorted.SortedAccessStrategyFactory;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;

/**
 * Index lookup strategy for subqueries.
 */
public class SubordSortedTableLookupStrategy implements SubordTableLookupStrategy
{
    private final SubordPropRangeKey rangeKey;

    /**
     * Index to look up in.
     */
    protected final PropertySortedEventTable index;

    protected final SortedAccessStrategy strategy;

    /**
     * Ctor.
     * @param index is the table carrying the data to lookup into
     */
    public SubordSortedTableLookupStrategy(boolean isNWOnTrigger, int numStreams, SubordPropRangeKey rangeKey, PropertySortedEventTable index)
    {
        this.rangeKey = rangeKey;
        this.index = index;
        this.strategy = SortedAccessStrategyFactory.make(isNWOnTrigger, -1, numStreams, rangeKey);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, ExprEvaluatorContext context)
    {
        return strategy.lookup(eventsPerStream, index, context);
    }

    public String toString()
    {
        return toQueryPlan();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " range " + rangeKey.toQueryPlan();
    }
}
