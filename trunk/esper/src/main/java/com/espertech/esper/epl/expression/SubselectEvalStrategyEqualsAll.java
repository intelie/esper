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
public class SubselectEvalStrategyEqualsAll implements SubselectEvalStrategy
{
    private final boolean isNot;
    private final boolean mustCoerce;
    private final Class coercionType;
    private final ExprNode valueExpr;
    private final ExprNode filterExpr;
    private final ExprNode selectClauseExpr;

    public SubselectEvalStrategyEqualsAll(boolean notIn, boolean mustCoerce, Class coercionType, ExprNode valueExpr, ExprNode selectClauseExpr, ExprNode filterExpr)
    {
        isNot = notIn;
        this.mustCoerce = mustCoerce;
        this.coercionType = coercionType;
        this.valueExpr = valueExpr;
        this.filterExpr = filterExpr;
        this.selectClauseExpr = selectClauseExpr;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents)
    {
        // Evaluate the child expression
        Object leftResult = valueExpr.evaluate(eventsPerStream, isNewData);

        if ((matchingEvents == null) || (matchingEvents.size() == 0))
        {
            if (leftResult == null)
            {
                return !isNot;
            }
            return isNot;
        }

        // Evaluation event-per-stream
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        if (isNot)
        {
            // Evaluate each select until we have a match
            for (EventBean event : matchingEvents)
            {
                events[0] = event;

                // Eval filter expression
                if (filterExpr != null)
                {
                    Boolean pass = (Boolean) filterExpr.evaluate(events, true);
                    if ((pass == null) || (!pass))
                    {
                        continue;
                    }
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
                    if (rightResult != null)
                    {
                        return null;
                    }
                    continue;
                }
                if (rightResult == null)
                {
                    return false;
                }

                if (!mustCoerce)
                {
                    if (leftResult.equals(rightResult))
                    {
                        return false;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (left.equals(right))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
        else
        {
            // Evaluate each select until we have a match
            for (EventBean event : matchingEvents)
            {
                events[0] = event;

                // Eval filter expression
                if (filterExpr != null)
                {
                    Boolean pass = (Boolean) filterExpr.evaluate(events, true);
                    if ((pass == null) || (!pass))
                    {
                        continue;
                    }
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
                        continue;
                    }
                    else
                    {
                        if (isNot)
                        {
                            continue;
                        }
                        return false;
                    }
                }
                if (rightResult == null)
                {
                    if (isNot)
                    {
                        continue;
                    }
                    return false;
                }

                if (!mustCoerce)
                {
                    if (leftResult.equals(rightResult))
                    {
                        if (isNot)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (!isNot)
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (left.equals(right))
                    {
                        if (isNot)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (!isNot)
                        {
                            return false;
                        }
                    }
                }
            }

            return isNot;

        }
    }
}