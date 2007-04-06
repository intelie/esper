package net.esper.regression.client;

import net.esper.pattern.observer.ObserverFactory;
import net.esper.pattern.observer.ObserverParameterException;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.ObserverEventEvaluator;
import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;

import java.util.List;

public class TimerSunsetObserverFactory implements ObserverFactory
{
    public void setObserverParameters(List<Object> observerParameters) throws ObserverParameterException
    {
        if (observerParameters != null)
        {
            throw new ObserverParameterException("Timer sunset observer takes no parameters");
        }
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId)
    {
        return new TimerSunsetObserver(context.getSchedulingService(), observerEventEvaluator); 
    }
}
