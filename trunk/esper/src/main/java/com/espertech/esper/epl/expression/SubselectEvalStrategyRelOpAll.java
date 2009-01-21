package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.type.RelationalOpEnum;

import java.util.Set;

public class SubselectEvalStrategyRelOpAll implements SubselectEvalStrategy
{
    private final RelationalOpEnum.Computer computer;
    private final ExprNode valueExpr;
    private final ExprNode selectClauseExpr;
    private final ExprNode filterExpr;

    public SubselectEvalStrategyRelOpAll(RelationalOpEnum.Computer computer, ExprNode valueExpr, ExprNode selectClause, ExprNode filterExpr)
    {
        this.computer = computer;
        this.valueExpr = valueExpr;
        this.selectClauseExpr = selectClause;
        this.filterExpr = filterExpr;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents)
    {
        if (matchingEvents == null)
        {
            return false;
        }
        if (matchingEvents.size() == 0)
        {
            return false;
        }

        // Evaluate the value expression
        Object valueLeft = valueExpr.evaluate(eventsPerStream, isNewData);

        // Evaluation event-per-stream
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        if (filterExpr == null)
        {
            // Evaluate each select until we have a match
            for (EventBean event : matchingEvents)
            {
                events[0] = event;

                Object valueRight;
                if (selectClauseExpr != null)
                {
                    valueRight = selectClauseExpr.evaluate(events, true);
                }
                else
                {
                    valueRight = events[0].getUnderlying();
                }

                if ((valueLeft == null) || (valueRight == null))
                {
                    return false;
                }

                if (!computer.compare(valueLeft, valueRight))
                {
                    return false;
                }
            }

            return true;
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

            Object valueRight;
            if (selectClauseExpr != null)
            {
                valueRight = selectClauseExpr.evaluate(events, true);
            }
            else
            {
                valueRight = events[0].getUnderlying();
            }

            if ((valueLeft == null) || (valueRight == null))
            {
                return false;
            }

            if (!computer.compare(valueLeft, valueRight))
            {
                return false;
            }
        }

        return true;
    }
}
