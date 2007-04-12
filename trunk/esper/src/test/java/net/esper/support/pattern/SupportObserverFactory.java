package net.esper.support.pattern;

import net.esper.pattern.observer.ObserverFactory;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.ObserverEventEvaluator;
import net.esper.pattern.observer.ObserverParameterException;
import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;

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
