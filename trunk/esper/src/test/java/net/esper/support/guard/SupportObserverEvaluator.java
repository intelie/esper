package net.esper.support.guard;

import java.util.LinkedList;
import java.util.List;

import net.esper.pattern.MatchedEventMap;
import net.esper.pattern.observer.ObserverEventEvaluator;

public class SupportObserverEvaluator implements ObserverEventEvaluator
{
    private List<MatchedEventMap> matchEvents = new LinkedList<MatchedEventMap>();
    private int evaluateFalseCounter;


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
}
