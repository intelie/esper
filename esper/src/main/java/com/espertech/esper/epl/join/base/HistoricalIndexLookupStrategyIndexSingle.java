/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.base;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.plan.QueryGraphValueEntryHashKeyed;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;

import java.util.Iterator;
import java.util.Set;

/**
 * Index lookup strategy into a poll-based cache result.
 */
public class HistoricalIndexLookupStrategyIndexSingle implements HistoricalIndexLookupStrategy
{
    private final EventBean[] eventsPerStream;
    private final ExprEvaluator evaluator;
    private final int lookupStream;

    /**
     * Ctor.
     */
    public HistoricalIndexLookupStrategyIndexSingle(int lookupStream, QueryGraphValueEntryHashKeyed hashKey)
    {
        this.eventsPerStream = new EventBean[lookupStream + 1];
        this.evaluator = hashKey.getKeyExpr().getExprEvaluator();
        this.lookupStream = lookupStream;
    }

    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable indexTable, ExprEvaluatorContext exprEvaluatorContext)
    {
        // The table may not be indexed as the cache may not actively cache, in which case indexing doesn't makes sense
        if (indexTable instanceof PropertyIndexedEventTableSingle)
        {
            PropertyIndexedEventTableSingle index = (PropertyIndexedEventTableSingle) indexTable;
            eventsPerStream[lookupStream] = lookupEvent;
            Object key = evaluator.evaluate(eventsPerStream, true, exprEvaluatorContext);

            Set<EventBean> events = index.lookup(key);
            if (events != null)
            {
                return events.iterator();
            }
            return null;
        }

        return indexTable.iterator();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " evaluator " + evaluator.getClass().getSimpleName();
    }
}
