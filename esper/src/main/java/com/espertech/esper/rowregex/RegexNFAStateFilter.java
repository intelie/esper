package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class RegexNFAStateFilter extends RegexNFAStateBase implements RegexNFAState
{
    private ExprNode exprNode;

    public RegexNFAStateFilter(String nodeNum, String variableName, int streamNum, boolean multiple, ExprNode exprNode)
    {
        super(nodeNum, variableName, streamNum, multiple, null);
        this.exprNode = exprNode;
    }

    public boolean matches(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext)
    {
        Boolean result = (Boolean) exprNode.evaluate(eventsPerStream, true, exprEvaluatorContext);
        if (result != null)
        {
            return result;
        }
        return false;
    }

    public String toString()
    {
        return "FilterEvent(" + exprNode.toExpressionString() + ")";
    }

}
