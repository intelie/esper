package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.List;

public interface RegexNFAState
{
    public boolean isMultiple();
    public String getNodeNumNested();
    public int getNodeNumFlat();
    public String getVariableName();
    public int getStreamNum();
    public Boolean isGreedy();
    public boolean matches(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext);
    public List<RegexNFAState> getNextStates();
}
