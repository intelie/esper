package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprNode;

public class RegexNFAStateZeroToMany extends RegexNFAStateBase implements RegexNFAState
{
    private ExprNode exprNode;

    public RegexNFAStateZeroToMany(String nodeNum, String variableName, int streamNum, boolean multiple, boolean isGreedy, ExprNode exprNode)
    {
        super(nodeNum, variableName, streamNum, multiple, isGreedy);
        this.exprNode = exprNode;
        this.addState(this);
    }

    public boolean matches(EventBean[] eventsPerStream)
    {
        if (exprNode == null)
        {
            return true;
        }
        Boolean result = (Boolean) exprNode.evaluate(eventsPerStream, true);
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
            return "ZeroMany-Unfiltered";            
        }
        return "ZeroMany-Filter(" + exprNode.toExpressionString() + ")";
    }

}
