package net.esper.pattern.observer;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSpec;
import net.esper.pattern.observer.EventObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Observer implementation for indicating that a certain time arrived, similar to "crontab".
 */
public class TimerAtObserver implements EventObserver, ScheduleCallback
{
    private final ScheduleSpec scheduleSpec;
    private final PatternContext context;
    private final MatchedEventMap beginState;
    private final ObserverEventEvaluator observerEventEvaluator;

    private boolean isTimerActive = false;

    /**
     * Ctor.
     * @param scheduleSpec - specification containing the crontab schedule
     * @param context - timer serive to use
     * @param beginState - start state
     * @param observerEventEvaluator - receiver for events
     */
    public TimerAtObserver(ScheduleSpec scheduleSpec, PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
    {
        this.scheduleSpec = scheduleSpec;
        this.context = context;
        this.beginState = beginState;
        this.observerEventEvaluator = observerEventEvaluator;
    }

    public final void scheduledTrigger()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".scheduledTrigger");
        }

        observerEventEvaluator.observerEvaluateTrue(beginState);
        isTimerActive = false;
    }

    public void startObserve()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".startObserve Starting at, spec=" + scheduleSpec);
        }

        if (isTimerActive == true)
        {
            throw new IllegalStateException("Timer already active");
        }

        context.getSchedulingService().add(scheduleSpec, this);
        isTimerActive = true;
    }

    public void stopObserve()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".stopObserve");
        }

        if (isTimerActive)
        {
            context.getSchedulingService().remove(this);
            isTimerActive = false;
        }
    }

    private static final Log log = LogFactory.getLog(TimerAtObserver.class);
}
