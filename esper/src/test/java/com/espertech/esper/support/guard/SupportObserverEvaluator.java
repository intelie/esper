/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
