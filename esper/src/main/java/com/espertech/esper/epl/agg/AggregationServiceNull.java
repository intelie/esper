/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

/**
 * A null object implementation of the AggregationService
 * interface.
 */
public class AggregationServiceNull implements AggregationService {

    public void applyEnter(EventBean[] eventsPerStream,
                           MultiKeyUntyped optionalGroupKeyPerRow,
                           ExprEvaluatorContext exprEvaluatorContext) {
    }

    public void applyLeave(EventBean[] eventsPerStream,
                           MultiKeyUntyped optionalGroupKeyPerRow,
                           ExprEvaluatorContext exprEvaluatorContext) {
    }

    public void setCurrentAccess(MultiKeyUntyped groupKey) {
    }

    public Object getValue(int column) {
        return null;
    }

    public Collection<EventBean> getCollection(int column) {
        return null;
    }

    public EventBean getEventBean(int column) {
        return null;
    }

    public void clearResults()
    {
        // no state to clear
    }
}
