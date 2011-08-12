/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public class NamedWindowOnMergeMatch
{
    private ExprEvaluator optionalCond;
    private List<NamedWindowOnMergeAction> actions;

    public NamedWindowOnMergeMatch(ExprNode optionalCond, List<NamedWindowOnMergeAction> actions) {
        this.optionalCond = optionalCond != null ? optionalCond.getExprEvaluator() : null;
        this.actions = actions;
    }

    public boolean isApplies(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        if (optionalCond == null) {
            return true;
        }
        Object result = optionalCond.evaluate(eventsPerStream, true, context);
        return result != null && (Boolean) result;
    }

    public void apply(EventBean matchingEvent, EventBean[] eventsPerStream, OneEventCollection newData, OneEventCollection oldData, ExprEvaluatorContext context) {
        for (NamedWindowOnMergeAction action : actions) {
            if (action.isApplies(eventsPerStream, context)) {
                action.apply(matchingEvent, eventsPerStream, newData, oldData, context);
            }
        }
    }
}