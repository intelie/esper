/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.guard;

import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.ExtensionServicesContext;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleSlot;

/**
 * Guard implementation that keeps a timer instance and quits when the timer expired,
 * and also keeps a count of the number of matches so far, checking both count and timer,
 * letting all {@link com.espertech.esper.pattern.MatchedEventMap} instances pass until then.
 */
public class TimerWithinOrMaxCountGuard implements Guard, ScheduleHandleCallback
{
    private final long msec;
    private final int numCountTo;
    private final PatternContext context;
    private final Quitable quitable;
    private final ScheduleSlot scheduleSlot;

    private int counter;
    private boolean isTimerActive;
    private EPStatementHandleCallback scheduleHandle;

    /**
     * Ctor.
     * @param msec - number of millisecond to guard expiration
     * @param numCountTo - max number of counts
     * @param context - contains timer service
     * @param quitable - to use to indicate that the gaurd quitted
     */
    public TimerWithinOrMaxCountGuard(long msec, int numCountTo, PatternContext context, Quitable quitable) {
        this.msec = msec;
        this.numCountTo = numCountTo;
        this.context = context;
        this.quitable = quitable;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();
    }

    public void startGuard() {
        if (isTimerActive) {
            throw new IllegalStateException("Timer already active");
        }

        scheduleHandle = new EPStatementHandleCallback(context.getEpStatementHandle(), this);
        context.getSchedulingService().add(msec, scheduleHandle, scheduleSlot);
        isTimerActive = true;
        counter = 0;
    }

    public boolean inspect(MatchedEventMap matchEvent) {
        counter++;
        if (counter > numCountTo) {
            quitable.guardQuit();
            deactivateTimer();
            return false;
        }
        return true;
    }

    public void stopGuard() {
        if (isTimerActive) {
            deactivateTimer();
        }
    }

    public void scheduledTrigger(ExtensionServicesContext extensionServicesContext) {
        // Timer callback is automatically removed when triggering
        isTimerActive = false;
        quitable.guardQuit();
    }

    private void deactivateTimer() {
        context.getSchedulingService().remove(scheduleHandle, scheduleSlot);
        scheduleHandle = null;
        isTimerActive = false;
    }
}