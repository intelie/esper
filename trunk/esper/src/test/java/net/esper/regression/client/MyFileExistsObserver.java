package net.esper.regression.client;

import net.esper.pattern.observer.EventObserver;
import net.esper.pattern.observer.ObserverEventEvaluator;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleSlot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.io.File;

public class MyFileExistsObserver implements EventObserver
{
    private final MatchedEventMap beginState;
    private final ObserverEventEvaluator observerEventEvaluator;
    private final String filename;

    public MyFileExistsObserver(MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, String filename)
    {
        this.beginState = beginState;
        this.observerEventEvaluator = observerEventEvaluator;
        this.filename = filename;
    }

    public void startObserve()
    {
        File file = new File(filename);
        if (file.exists())
        {
            observerEventEvaluator.observerEvaluateTrue(beginState);
        }
        else
        {
            observerEventEvaluator.observerEvaluateFalse();
        }
    }

    public void stopObserve()
    {
        // this is called when the subexpression quits or the pattern is stopped
        // no action required
    }
}
