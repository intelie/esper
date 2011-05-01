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
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprNodeUtility;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;

import java.util.Collection;
import java.util.List;

/**
 * Index lookup strategy for subqueries.
 */
public class SubordIndexedTableLookupStrategyExpr implements SubordTableLookupStrategy
{
    /**
     * Index to look up in.
     */
    protected final PropertyIndexedEventTable index;

    protected final ExprEvaluator[] evaluators;
    private EventBean[] events;
    private final boolean isNWOnTrigger;

    /**
     * Ctor.
     * @param index is the table carrying the data to lookup into
     */
    public SubordIndexedTableLookupStrategyExpr(boolean isNWOnTrigger, int numStreamsOuter, List<SubordPropHashKey> hashKeys, PropertyIndexedEventTable index)
    {
        evaluators = new ExprEvaluator[hashKeys.size()];
        events = new EventBean[numStreamsOuter + 1];
        for (int i = 0; i < hashKeys.size(); i++) {
            evaluators[i] = hashKeys.get(i).getHashKey().getKeyExpr().getExprEvaluator();
        }
        this.index = index;
        this.isNWOnTrigger = isNWOnTrigger;
    }

    /**
     * Returns index to look up in.
     * @return index to use
     */
    public PropertyIndexedEventTable getIndex()
    {
        return index;
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, ExprEvaluatorContext context)
    {
        Object[] keys = getKeys(eventsPerStream, context);
        return index.lookup(keys);
    }

    /**
     * Get the index lookup keys.
     * @param eventsPerStream is the events for each stream
     * @return key object
     */
    protected Object[] getKeys(EventBean[] eventsPerStream, ExprEvaluatorContext context)
    {
        EventBean[] eventsToUse;
        if (isNWOnTrigger) {
            eventsToUse = eventsPerStream;
        }
        else {
            System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);
            eventsToUse = events;
        }
        Object[] keyValues = new Object[evaluators.length];
        for (int i = 0; i < evaluators.length; i++)
        {
            keyValues[i] = evaluators[i].evaluate(eventsToUse, true, context);
        }
        return keyValues;
    }

    public String toString()
    {
        return toQueryPlan();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " evaluators " + ExprNodeUtility.printEvaluators(evaluators);
    }
}
