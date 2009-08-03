package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.List;
import java.util.Collections;

public class RegexNFAStateEnd extends RegexNFAStateBase
{
    public RegexNFAStateEnd() {
        super("endstate", null, -1, false, null);
    }

    public boolean matches(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext)
    {
        throw new UnsupportedOperationException();
    }

    public List<RegexNFAState> getNextStates()
    {
        return Collections.EMPTY_LIST;
    }
}
