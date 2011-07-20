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
import com.espertech.esper.epl.expression.ExprNodeUtility;
import com.espertech.esper.epl.join.plan.QueryGraphValueEntryHashKeyed;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Index lookup strategy into a poll-based cache result.
 */
public class HistoricalIndexLookupStrategyIndex implements HistoricalIndexLookupStrategy
{
    private final EventBean[] eventsPerStream;
    private final int lookupStream;
    private final ExprEvaluator[] evaluators;

    /**
     * Ctor.
     * @param eventType - event type to expect for lookup
     */
    public HistoricalIndexLookupStrategyIndex(EventType eventType, int lookupStream, List<QueryGraphValueEntryHashKeyed> hashKeys)
    {
        this.evaluators = new ExprEvaluator[hashKeys.size()];
        for (int i = 0; i < hashKeys.size(); i++) {
            evaluators[i] = hashKeys.get(i).getKeyExpr().getExprEvaluator();
        }
        this.eventsPerStream = new EventBean[lookupStream + 1];
        this.lookupStream = lookupStream;
    }

    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable indexTable, ExprEvaluatorContext exprEvaluatorContext)
    {
        // The table may not be indexed as the cache may not actively cache, in which case indexing doesn't makes sense
        if (indexTable instanceof PropertyIndexedEventTable)
        {
            PropertyIndexedEventTable index = (PropertyIndexedEventTable) indexTable;
            Object[] keys = getKeys(lookupEvent, exprEvaluatorContext);

            Set<EventBean> events = index.lookup(keys);
            if (events != null)
            {
                return events.iterator();
            }
            return null;
        }

        return indexTable.iterator();
    }

    private Object[] getKeys(EventBean event, ExprEvaluatorContext exprEvaluatorContext)
    {
        eventsPerStream[lookupStream] = event;
        Object[] keys = new Object[evaluators.length];
        for (int i = 0; i < evaluators.length; i++) {
            keys[i] = evaluators[i].evaluate(eventsPerStream, true, exprEvaluatorContext);
        }
        return keys;
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " evaluators " + ExprNodeUtility.printEvaluators(evaluators);
    }
}
