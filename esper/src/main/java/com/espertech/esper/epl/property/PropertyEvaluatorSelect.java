/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.property;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.ArrayDeque;

/**
 * Property evaluator that considers a select-clauses and relies
 * on an accumulative property evaluator that presents events for all columns and rows.
 */
public class PropertyEvaluatorSelect implements PropertyEvaluator
{
    private final SelectExprProcessor selectExprProcessor;
    private final PropertyEvaluatorAccumulative accumulative;

    /**
     * Ctor.
     * @param selectExprProcessor evaluates the select clause
     * @param accumulative provides property events for input events
     */
    public PropertyEvaluatorSelect(SelectExprProcessor selectExprProcessor, PropertyEvaluatorAccumulative accumulative)
    {
        this.selectExprProcessor = selectExprProcessor;
        this.accumulative = accumulative;
    }

    public EventBean[] getProperty(EventBean event, ExprEvaluatorContext exprEvaluatorContext)
    {
        ArrayDeque<EventBean[]> rows = accumulative.getAccumulative(event, exprEvaluatorContext);
        if ((rows == null) || (rows.isEmpty()))
        {
            return null;
        }
        ArrayDeque<EventBean> result = new ArrayDeque<EventBean>();
        for (EventBean[] row : rows)
        {
            EventBean bean = selectExprProcessor.process(row, true, false);
            result.add(bean);
        }
        return result.toArray(new EventBean[result.size()]);
    }

    public EventType getFragmentEventType()
    {
        return selectExprProcessor.getResultEventType();
    }

    public boolean compareTo(PropertyEvaluator otherFilterPropertyEval)
    {
        return false;
    }
}