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
import com.espertech.esper.epl.join.exec.composite.InnerIndexQuery;
import com.espertech.esper.epl.join.exec.composite.InnerIndexQueryFactory;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.Arrays;
import java.util.Collection;

/**
 * Index lookup strategy for subqueries.
 */
public class SubqCompositeTableLookupStrategy implements SubqTableLookupStrategy
{
    private final PropertyCompositeEventTable index;
    private final InnerIndexQuery innerIndexQuery;
    private final Collection<SubqueryRangeKeyDesc> rangeDescs;

    public SubqCompositeTableLookupStrategy(EventType[] typesPerStream, int[] keyStreamNums, String[] keyProps, Class[] coercionKeyTypes, Collection<SubqueryRangeKeyDesc> rangeProps, Class[] coercionRangeTypes, PropertyCompositeEventTable index) {
        this.index = index;
        this.rangeDescs = rangeProps;
        this.innerIndexQuery = InnerIndexQueryFactory.make(typesPerStream, keyStreamNums, keyProps, coercionKeyTypes, rangeProps, coercionRangeTypes);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream)
    {
        return innerIndexQuery.get(eventsPerStream, index.getIndex());
    }

    public String toString()
    {
        return "SubqCompositeTableLookupStrategy ranges=" + Arrays.toString(rangeDescs.toArray()) +
                " index=(" + index + ')';
    }
}
