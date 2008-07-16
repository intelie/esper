package com.espertech.esper.epl.view;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.MultiKey;

import java.util.Set;

public class OutputConditionExpression implements OutputCondition
{
    private final ExprNode whenExpressionNode;
    private final OutputCallback outputCallback;

    public OutputConditionExpression(ExprNode whenExpressionNode, StatementContext statementContext, OutputCallback outputCallback)
    {
        this.whenExpressionNode = whenExpressionNode;
        this.outputCallback = outputCallback;
    }

    public void updateOutputCondition(int newEventsCount, int oldEventsCount, Set<MultiKey<EventBean>> newEvents, EventBean[] newData)
    {
        boolean isOutput = false;

        if (newEvents != null)
        {
            for (MultiKey<EventBean> row : newEvents)
            {
                Boolean output = (Boolean) whenExpressionNode.evaluate(row.getArray(), true);
                if ((output != null) && (output))
                {
                    isOutput = true;
                    break;
                }
            }
        }

        if (newData != null)
        {
            EventBean[] row = new EventBean[1];
            for (EventBean event : newData)
            {
                row[0] = event;
                Boolean output = (Boolean) whenExpressionNode.evaluate(row, true);
                if ((output != null) && (output))
                {
                    isOutput = true;
                    break;
                }
            }
        }

        if (isOutput)
        {
            outputCallback.continueOutputProcessing(true, false);
        }
    }
}
