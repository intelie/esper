/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.observer;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleSpec;
import net.esper.pattern.observer.TimerIntervalObserver;
import net.esper.pattern.observer.ObserverFactory;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.TimerAtObserver;
import net.esper.eql.parse.TimePeriodParameter;

/**
 * Factory for making observer instances.
 */
public class TimerObserverFactory implements ObserverFactory
{
    private ScheduleSpec scheduleSpec;
    private long msec;

    /**
     * Ctor.
     * @param scheduleSpec - schedule definition.
     */
    public TimerObserverFactory(ScheduleSpec scheduleSpec)
    {
        this.scheduleSpec = scheduleSpec;
    }

    /**
     * Ctor.
     * @param seconds - time in seconds.
     */
    public TimerObserverFactory(int seconds)
    {
        this.msec = seconds * 1000;
    }

    /**
     * Ctor.
     * @param seconds - time in seconds.
     */
    public TimerObserverFactory(double seconds)
    {
        this.msec = Math.round(seconds * 1000d);
    }

    /**
     * Ctor.
     * @param timePeriodParameter - time in seconds.
     */
    public TimerObserverFactory(TimePeriodParameter timePeriodParameter)
    {
        this.msec = Math.round(timePeriodParameter.getNumSeconds() * 1000d);
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
    {
        if (scheduleSpec != null)
        {
            return new TimerAtObserver(scheduleSpec, context, beginState, observerEventEvaluator);
        }
        else
        {
            return new TimerIntervalObserver(msec, context, beginState, observerEventEvaluator);
        }
    }
}
