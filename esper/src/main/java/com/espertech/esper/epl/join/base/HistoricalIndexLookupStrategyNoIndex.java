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
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.table.EventTable;

import java.util.Iterator;

/**
 * Full table scan strategy for a poll-based cache result.
 */
public class HistoricalIndexLookupStrategyNoIndex implements HistoricalIndexLookupStrategy
{
    public HistoricalIndexLookupStrategyNoIndex() {
    }

    public Iterator<EventBean> lookup(EventBean lookupEvent, EventTable index, ExprEvaluatorContext context) {
        return index.iterator();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName();
    }
}
