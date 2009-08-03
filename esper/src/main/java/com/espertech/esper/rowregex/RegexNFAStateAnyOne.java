package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class RegexNFAStateAnyOne extends RegexNFAStateBase implements RegexNFAState
{
    public RegexNFAStateAnyOne(String nodeNum, String variableName, int streamNum, boolean multiple)
    {
        super(nodeNum, variableName, streamNum, multiple, null);
    }

    public boolean matches(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext)
    {
        return true;
    }

    public String toString()
    {
        return "AnyEvent";
    }
}
