/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Set;

/**
 * Represents a in-subselect evaluation strategy.
 */
public class SubselectEvalStrategyEqualsIn implements SubselectEvalStrategy
{
    private final boolean isNotIn;
    private final boolean mustCoerce;
    private final Class coercionType;
    private final ExprNode valueExpr;
    private final ExprNode filterExpr;
    private final ExprNode selectClauseExpr;

    public SubselectEvalStrategyEqualsIn(boolean notIn, boolean mustCoerce, Class coercionType, ExprNode valueExpr, ExprNode selectClauseExpr, ExprNode filterExpr)
    {
        isNotIn = notIn;
        this.mustCoerce = mustCoerce;
        this.coercionType = coercionType;
        this.valueExpr = valueExpr;
        this.filterExpr = filterExpr;
        this.selectClauseExpr = selectClauseExpr;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents)
    {
        if (matchingEvents == null)
        {
            return isNotIn;
        }
        if (matchingEvents.size() == 0)
        {
            return isNotIn;
        }

        // Evaluate the child expression
        Object leftResult = valueExpr.evaluate(eventsPerStream, isNewData);

        // Evaluation event-per-stream
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        if (filterExpr == null)
        {
            // Evaluate each select until we have a match
            for (EventBean event : matchingEvents)
            {
                events[0] = event;

                Object rightResult;
                if (selectClauseExpr != null)
                {
                    rightResult = selectClauseExpr.evaluate(events, true);
                }
                else
                {
                    rightResult = events[0].getUnderlying();
                }

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        return !isNotIn;
                    }
                    continue;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (!mustCoerce)
                {
                    if (leftResult.equals(rightResult))
                    {
                        return !isNotIn;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (left.equals(right))
                    {
                        return !isNotIn;
                    }
                }
            }

            return isNotIn;
        }

        // Filter and check each row.
        for (EventBean subselectEvent : matchingEvents)
        {
            // Prepare filter expression event list
            events[0] = subselectEvent;

            // Eval filter expression
            Boolean pass = (Boolean) filterExpr.evaluate(events, true);
            if ((pass == null) || (!pass))
            {
                continue;
            }

            Object rightResult;
            if (selectClauseExpr != null)
            {
                rightResult = selectClauseExpr.evaluate(events, true);
            }
            else
            {
                rightResult = events[0].getUnderlying();
            }

            if (leftResult == null)
            {
                if (rightResult == null)
                {
                    return !isNotIn;
                }
                continue;
            }
            if (rightResult == null)
            {
                continue;
            }
            if (!mustCoerce)
            {
                if (leftResult.equals(rightResult))
                {
                    return !isNotIn;
                }
            }
            else
            {
                Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                if (left.equals(right))
                {
                    return !isNotIn;
                }
            }
        }

        return isNotIn;
    }
}