package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class RegexNFAStateOneOptional extends RegexNFAStateBase implements RegexNFAState
{
    private ExprNode exprNode;

    public RegexNFAStateOneOptional(String nodeNum, String variableName, int streamNum, boolean multiple, boolean isGreedy, ExprNode exprNode)
    {
        super(nodeNum, variableName, streamNum, multiple, isGreedy);
        this.exprNode = exprNode;
    }

    public boolean matches(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext)
    {
        if (exprNode == null)
        {
            return true;
        }

        Boolean result = (Boolean) exprNode.evaluate(eventsPerStream, true, exprEvaluatorContext);
        if (result != null)
        {
            return result;
        }
        return false;
    }

    public String toString()
    {
        if (exprNode == null)
        {
            return "OptionalFilterEvent";
        }
        return "OptionalFilterEvent(" + exprNode.toExpressionString() + ")";
    }

}
