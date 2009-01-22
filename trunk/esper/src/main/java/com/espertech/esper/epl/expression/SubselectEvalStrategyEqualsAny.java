package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Set;

/**
 * Represents a in-subselect evaluation strategy.
 */
public class SubselectEvalStrategyEqualsAny implements SubselectEvalStrategy
{
    private final boolean isNot;
    private final boolean mustCoerce;
    private final Class coercionType;
    private final ExprNode valueExpr;
    private final ExprNode filterExpr;
    private final ExprNode selectClauseExpr;

    public SubselectEvalStrategyEqualsAny(boolean notIn, boolean mustCoerce, Class coercionType, ExprNode valueExpr, ExprNode selectClauseExpr, ExprNode filterExpr)
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
        if ((matchingEvents == null) || (matchingEvents.size() == 0))
        {
            return false;
        }

        // Evaluate the child expression
        Object leftResult = valueExpr.evaluate(eventsPerStream, isNewData);

        // Evaluation event-per-stream
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        if (isNot)
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

                // Eval filter expression
                if (filterExpr != null)
                {
                    Boolean pass = (Boolean) filterExpr.evaluate(events, true);
                    if ((pass == null) || (!pass))
                    {
                        continue;
                    }
                }

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        continue;
                    }
                    return true;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (!mustCoerce)
                {
                    if (!leftResult.equals(rightResult))
                    {
                        return true;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (!left.equals(right))
                    {
                        return true;
                    }
                }
            }

            return false;
        }
        else
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

                // Eval filter expression
                if (filterExpr != null)
                {
                    Boolean pass = (Boolean) filterExpr.evaluate(events, true);
                    if ((pass == null) || (!pass))
                    {
                        continue;
                    }
                }

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        return true;
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
                        return true;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (left.equals(right))
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}
