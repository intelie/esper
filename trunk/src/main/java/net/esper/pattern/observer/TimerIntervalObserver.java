package net.esper.pattern.observer;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSlot;

/**
 * Observer that will wait a certain interval before indicating true (raising an event).
 */
public class TimerIntervalObserver implements EventObserver, ScheduleCallback
{
    private final long msec;
    private final PatternContext context;
    private final MatchedEventMap beginState;
    private final ObserverEventEvaluator observerEventEvaluator;
    private final ScheduleSlot scheduleSlot;

    private boolean isTimerActive = false;

    /**
     * Ctor.
     * @param msec - number of milliseconds
     * @param context - timer service
     * @param beginState - start state
     * @param observerEventEvaluator - receiver for events
     */
    public TimerIntervalObserver(long msec, PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
    {
        this.msec = msec;
        this.context = context;
        this.beginState = beginState;
        this.observerEventEvaluator = observerEventEvaluator;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();
    }

    public final void scheduledTrigger()
    {
        observerEventEvaluator.observerEvaluateTrue(beginState);
        isTimerActive = false;
    }

    public void startObserve()
    {
        if (isTimerActive == true)
        {
            throw new IllegalStateException("Timer already active");
        }

        if (msec <= 0)
        {
            observerEventEvaluator.observerEvaluateTrue(beginState);
        }
        else
        {
            context.getSchedulingService().add(msec, this, scheduleSlot);
            isTimerActive = true;
        }
    }

    public void stopObserve()
    {
        if (isTimerActive)
        {
            context.getSchedulingService().remove(this, scheduleSlot);
            isTimerActive = false;
        }
    }
}
