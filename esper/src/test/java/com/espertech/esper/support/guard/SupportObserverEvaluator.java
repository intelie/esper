package com.espertech.esper.support.guard;

import java.util.LinkedList;
import java.util.List;

import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.observer.ObserverEventEvaluator;

public class SupportObserverEvaluator implements ObserverEventEvaluator
{
    private List<MatchedEventMap> matchEvents = new LinkedList<MatchedEventMap>();
    private int evaluateFalseCounter;
    private PatternContext context;

    public SupportObserverEvaluator(PatternContext context) {
        this.context = context;
    }

    public void observerEvaluateTrue(MatchedEventMap matchEvent)
    {
        matchEvents.add(matchEvent);
    }

    public void observerEvaluateFalse()
    {
        evaluateFalseCounter++;
    }

    public List<MatchedEventMap> getAndClearMatchEvents()
    {
        List<MatchedEventMap> original = matchEvents;
        matchEvents = new LinkedList<MatchedEventMap>();
        return original;
    }

    public List<MatchedEventMap> getMatchEvents()
    {
        return matchEvents;
    }

    public int getAndResetEvaluateFalseCounter()
    {
        int value = evaluateFalseCounter;
        evaluateFalseCounter = 0;
        return value;
    }

    @Override
    public PatternContext getContext() {
        return context;
    }
}
