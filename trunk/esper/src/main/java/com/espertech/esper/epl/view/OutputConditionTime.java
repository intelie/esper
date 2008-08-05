/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.view;

import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.ExtensionServicesContext;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.variable.VariableReader;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleSlot;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Output condition that is satisfied at the end
 * of every time interval of a given length.
 */
public final class OutputConditionTime implements OutputCondition
{
    private static final boolean DO_OUTPUT = true;
	private static final boolean FORCE_UPDATE = true;

    private long msecIntervalSize;
    private final OutputCallback outputCallback;
    private final ScheduleSlot scheduleSlot;

    private Long currentReferencePoint;
    private StatementContext context;
    private boolean isCallbackScheduled;
    private final VariableReader reader;
    private EPStatementHandleCallback handle;
    private boolean isMinutesUnit;

    /**
     * Constructor.
     * @param intervalSize is the number of minutes or seconds to batch events for.
     * @param context is the view context for time scheduling
     * @param outputCallback is the callback to make once the condition is satisfied
     * @param reader is for reading the variable value, if a variable was supplied, else null
     * @param isMinutesUnit is true to indicate the unit is minutes, or false for the unit as seconds
     */
    public OutputConditionTime(Double intervalSize,
                               boolean isMinutesUnit,
                               VariableReader reader,
                               StatementContext context,
    						   OutputCallback outputCallback)
    {
		if(outputCallback ==  null)
		{
			throw new NullPointerException("Output condition by count requires a non-null callback");
		}
        if (!isMinutesUnit)
        {
            if ((intervalSize < 0.001) && (reader == null))
            {
                throw new IllegalArgumentException("Output condition by time requires a millisecond interval size of at least 1 msec or a variable");
            }
        }
        if (context == null)
        {
            String message = "OutputConditionTime requires a non-null view context";
            throw new NullPointerException(message);
        }

        this.reader = reader;
        this.context = context;
        this.outputCallback = outputCallback;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();
        this.isMinutesUnit = isMinutesUnit;

        if (reader != null)
        {
            intervalSize = ((Number)reader.getValue()).doubleValue();
        }
        if (isMinutesUnit)
        {
            intervalSize = intervalSize * 60d;
        }
        this.msecIntervalSize = Math.round(1000 * intervalSize);
    }

    /**
     * Returns the interval size in milliseconds.
     * @return batch size
     */
    public final long getMsecIntervalSize()
    {
        return msecIntervalSize;
    }

    public final void updateOutputCondition(int newEventsCount, int oldEventsCount)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
        	log.debug(".updateOutputCondition, " +
        			"  newEventsCount==" + newEventsCount +
        			"  oldEventsCount==" + oldEventsCount);
        }

        if (currentReferencePoint == null)
        {
        	currentReferencePoint = context.getSchedulingService().getTime();
        }

        // If we pull the interval from a variable, then we may need to reschedule
        if (reader != null)
        {
            Object value = reader.getValue();
            if (value != null)
            {
                // Check if the variable changed
                double intervalSize = ((Number)reader.getValue()).doubleValue();
                if (isMinutesUnit)
                {
                    intervalSize = intervalSize * 60d;
                }

                long newMsecIntervalSize = Math.round(1000 * intervalSize);

                // reschedule if the interval changed
                if (newMsecIntervalSize != msecIntervalSize)
                {
                    if (isCallbackScheduled)
                    {
                        // reschedule
                        context.getSchedulingService().remove(handle, scheduleSlot);
                        scheduleCallback();
                    }
                }
            }
        }

        // Schedule the next callback if there is none currently scheduled
        if (!isCallbackScheduled)
        {
        	scheduleCallback();
        }
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " msecIntervalSize=" + msecIntervalSize;
    }

    private void scheduleCallback()
    {
        // If we pull the interval from a variable, get the current interval length
        if (reader != null)
        {
            Object value = reader.getValue();
            if (value != null)
            {
                // Check if the variable changed
                double intervalSize = ((Number)reader.getValue()).doubleValue();
                if (isMinutesUnit)
                {
                    intervalSize = intervalSize * 60d;
                }
                msecIntervalSize = Math.round(1000 * intervalSize);
            }
        }

    	isCallbackScheduled = true;
        long current = context.getSchedulingService().getTime();
        long afterMSec = computeWaitMSec(current, this.currentReferencePoint, this.msecIntervalSize);

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".scheduleCallback Scheduled new callback for " +
                    " afterMsec=" + afterMSec +
                    " now=" + current +
                    " currentReferencePoint=" + currentReferencePoint +
                    " msecIntervalSize=" + msecIntervalSize);
        }

        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                OutputConditionTime.this.isCallbackScheduled = false;
                OutputConditionTime.this.outputCallback.continueOutputProcessing(DO_OUTPUT, FORCE_UPDATE);
                scheduleCallback();
            }
        };
        handle = new EPStatementHandleCallback(context.getEpStatementHandle(), callback);
        context.getSchedulingService().add(afterMSec, handle, scheduleSlot);
    }

    /**
     * Given a current time and a reference time and an interval size, compute the amount of
     * milliseconds till the next interval.
     * @param current is the current time
     * @param reference is the reference point
     * @param interval is the interval size
     * @return milliseconds after current time that marks the end of the current interval
     */
    protected static long computeWaitMSec(long current, long reference, long interval)
    {
        // Example:  current c=2300, reference r=1000, interval i=500, solution s=200
        //
        // int n = ((2300 - 1000) / 500) = 2
        // r + (n + 1) * i - c = 200
        //
        // Negative example:  current c=2300, reference r=4200, interval i=500, solution s=400
        // int n = ((2300 - 4200) / 500) = -3
        // r + (n + 1) * i - c = 4200 - 3*500 - 2300 = 400
        //
        long n = (long) ( (current - reference) / (interval * 1f));
        if (reference > current)        // References in the future need to deduct one window
        {
            n = n - 1;
        }
        long solution = reference + (n + 1) * interval - current;

        if (solution == 0)
        {
            return interval;
        }
        return solution;
    }

    private static final Log log = LogFactory.getLog(OutputConditionTime.class);
}