package com.espertech.esper.regression.client;

import com.espertech.esper.pattern.observer.*;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.ViewFactorySupport;

import java.util.List;

public class MyFileExistsObserverFactory extends ObserverFactorySupport
{
    private String filename;

    public void setObserverParameters(List<ExprNode> expressionParameters) throws ObserverParameterException
    {
        List<Object> observerParameters = EventObserverSupport.evaluate("File-exists observer ", expressionParameters);
        String message = "File exists observer takes a single string filename parameter";
        if (observerParameters.size() != 1)
        {
            throw new ObserverParameterException(message);
        }
        if (!(observerParameters.get(0) instanceof String))
        {
            throw new ObserverParameterException(message);
        }

        filename = observerParameters.get(0).toString();
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId, Object observerState)
    {
        return new MyFileExistsObserver(beginState, observerEventEvaluator, filename);
    }
}
