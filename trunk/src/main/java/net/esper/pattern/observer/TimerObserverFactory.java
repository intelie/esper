package net.esper.pattern.observer;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleSpec;
import net.esper.pattern.observer.TimerIntervalObserver;
import net.esper.pattern.observer.ObserverFactory;
import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.TimerAtObserver;

/**
 * Factory for making observer instances.
 */
public class TimerObserverFactory implements ObserverFactory
{
    private ScheduleSpec scheduleSpec;
    private int msec;

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
     * @param msec - time in millis.
     */
    public TimerObserverFactory(int msec)
    {
        this.msec = msec;
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
