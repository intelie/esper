package net.esper.regression.client;

import net.esper.pattern.observer.*;
import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;

import java.util.List;

public class MyFileExistsObserverFactory extends ObserverFactorySupport
{
    private String filename;

    public void setObserverParameters(List<Object> observerParameters) throws ObserverParameterException
    {
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