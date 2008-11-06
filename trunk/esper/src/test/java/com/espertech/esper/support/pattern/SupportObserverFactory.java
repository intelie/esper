package com.espertech.esper.support.pattern;

import com.espertech.esper.pattern.observer.ObserverFactory;
import com.espertech.esper.pattern.observer.EventObserver;
import com.espertech.esper.pattern.observer.ObserverEventEvaluator;
import com.espertech.esper.pattern.observer.ObserverParameterException;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMap;

import java.util.List;

public class SupportObserverFactory implements ObserverFactory
{
    public void setObserverParameters(List<Object> observerParameters) throws ObserverParameterException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId, Object observerState)
    {
        return null;
    }
}
