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
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexQuery;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexQueryFactory;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;

import java.util.Arrays;
import java.util.Collection;

/**
 * Index lookup strategy for subqueries.
 */
public class SubordCompositeTableLookupStrategy implements SubordTableLookupStrategy
{
    private final PropertyCompositeEventTable index;
    private final CompositeIndexQuery innerIndexQuery;
    private final Collection<SubordPropRangeKey> rangeDescs;

    public SubordCompositeTableLookupStrategy(boolean isNWOnTrigger, int numStreams, Collection<SubordPropHashKey> keyExpr, Class[] coercionKeyTypes, Collection<SubordPropRangeKey> rangeProps, Class[] coercionRangeTypes, PropertyCompositeEventTable index) {
        this.index = index;
        this.rangeDescs = rangeProps;
        this.innerIndexQuery = CompositeIndexQueryFactory.makeSubordinate(isNWOnTrigger, numStreams, keyExpr, coercionKeyTypes, rangeProps, coercionRangeTypes);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, ExprEvaluatorContext context)
    {
        return innerIndexQuery.get(eventsPerStream, index.getIndex(), context);
    }

    public Collection<EventBean> lookup(Object[] keys) {
        return null;
    }

    public String toString()
    {
        return toQueryPlan();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " ranges=" + SubordPropRangeKey.toQueryPlan(rangeDescs);
    }

}
