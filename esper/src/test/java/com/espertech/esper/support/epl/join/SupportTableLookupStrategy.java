package com.espertech.esper.support.epl.join;

import com.espertech.esper.epl.join.exec.base.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.rep.Cursor;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.client.EventBean;

import java.util.Set;

public class SupportTableLookupStrategy implements JoinExecTableLookupStrategy
{
    private final int numResults;

    public SupportTableLookupStrategy(int numResults)
    {
        this.numResults = numResults;
    }

    public Set<EventBean> lookup(EventBean event, Cursor cursor, ExprEvaluatorContext exprEvaluatorContext)
    {
        return SupportJoinResultNodeFactory.makeEventSet(numResults);
    }
}
