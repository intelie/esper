/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.guard;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.core.EPStatementHandleCallback;
import net.esper.core.ExtensionServicesContext;

/**
 * Guard implementation that keeps a timer instance and quits when the timer expired,
 * letting all {@link MatchedEventMap} instances pass until then.
 */
public class TimerWithinGuard implements Guard, ScheduleHandleCallback
{
    private final long msec;
    private final PatternContext context;
    private final Quitable quitable;
    private final ScheduleSlot scheduleSlot;

    private boolean isTimerActive;
    private EPStatementHandleCallback scheduleHandle;

    /**
     * Ctor.
     * @param msec - number of millisecond to guard expiration
     * @param context - contains timer service
     * @param quitable - to use to indicate that the gaurd quitted
     */
    public TimerWithinGuard(long msec, PatternContext context, Quitable quitable)
    {
        this.msec = msec;
        this.context = context;
        this.quitable = quitable;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();
    }

    public void startGuard()
    {
        if (isTimerActive == true)
        {
            throw new IllegalStateException("Timer already active");
        }

        // Start the stopwatch timer
        scheduleHandle = new EPStatementHandleCallback(context.getEpStatementHandle(), this);
        context.getSchedulingService().add(msec, scheduleHandle, scheduleSlot);
        isTimerActive = true;
    }

    public void stopGuard()
    {
        if (isTimerActive)
        {
            context.getSchedulingService().remove(scheduleHandle, scheduleSlot);
            scheduleHandle = null;
            isTimerActive = false;
        }
    }

    public boolean inspect(MatchedEventMap matchEvent)
    {
        // no need to test: for timing only, if the timer expired the guardQuit stops any events from coming here
        return true;
    }

    public final void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
    {
        // Timer callback is automatically removed when triggering
        isTimerActive = false;
        quitable.guardQuit();
    }
}
