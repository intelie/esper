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
import com.espertech.esper.epl.join.plan.RangeKeyDesc;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;
import com.espertech.esper.epl.join.table.comp.InnerIndexQuery;
import com.espertech.esper.epl.join.table.comp.InnerIndexQueryFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Index lookup strategy for subqueries.
 */
public class CompositeTableLookupStrategy implements TableLookupStrategy
{
    private final PropertyCompositeEventTable index;
    private final InnerIndexQuery innerIndexQuery;
    private final List<RangeKeyDesc> rangeDescs;

    public CompositeTableLookupStrategy(EventType[] eventTypes, int[] keyStreamNums, String[] keyProps, Class[] coercionTypes, int[] rangeStreamNums, List<RangeKeyDesc> rangeDescs, Class[] rangeCoercionTypes, PropertyCompositeEventTable index) {
        this.index = index;
        this.rangeDescs = rangeDescs;

        // TODO - not right yet
        innerIndexQuery = InnerIndexQueryFactory.make(eventTypes[0], keyProps, coercionTypes, rangeDescs, rangeCoercionTypes);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream)
    {
        // TODO - plain wrong
        return innerIndexQuery.get(eventsPerStream[0], index.getIndex());
    }

    public String toString()
    {
        // TODO - improve
        return "SortedTableLookupStrategy ranges=" + Arrays.toString(rangeDescs.toArray()) +
                " index=(" + index + ')';
    }
}
