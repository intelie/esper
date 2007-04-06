package net.esper.regression.client;

import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.ObserverEventEvaluator;
import net.esper.schedule.SchedulingService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

// TODO: complete observer
public class TimerSunsetObserver implements EventObserver
{
    private SchedulingService schedulingService;
    private ObserverEventEvaluator observerEventEvaluator;

    public TimerSunsetObserver(SchedulingService schedulingService, ObserverEventEvaluator observerEventEvaluator)
    {
        Calendar now = GregorianCalendar.getInstance();
        now.setTime(new Date(schedulingService.getTime()));
    }

    public void startObserve()
    {

    }

    public void stopObserve()
    {
        
    }
}
