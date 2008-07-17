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
import com.espertech.esper.schedule.*;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.MultiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

public final class OutputConditionCrontab implements OutputCondition
{
    private static final boolean DO_OUTPUT = true;
	private static final boolean FORCE_UPDATE = true;

    private final OutputCallback outputCallback;
    private final ScheduleSlot scheduleSlot;

    private Long currentReferencePoint;
    private StatementContext context;
    private boolean isCallbackScheduled;
    private EPStatementHandleCallback handle;
    private ScheduleSpec scheduleSpec;

    /**
     * Constructor.
     * @param context is the view context for time scheduling
     * @param outputCallback is the callback to make once the condition is satisfied
     */
    public OutputConditionCrontab(Object[] scheduleSpecParameterList,
                                   StatementContext context,
                                   OutputCallback outputCallback)
    {
		if(outputCallback ==  null)
		{
			throw new NullPointerException("Output condition crontab requires a non-null callback");
		}
        if (context == null)
        {
            String message = "OutputConditionTime requires a non-null view context";
            throw new NullPointerException(message);
        }

        this.context = context;
        this.outputCallback = outputCallback;
        this.scheduleSlot = context.getScheduleBucket().allocateSlot();

        try
        {
            scheduleSpec = ScheduleSpecUtil.computeValues(scheduleSpecParameterList);
        }
        catch (ScheduleParameterException e)
        {
            throw new IllegalArgumentException("Invalid schedule specification : " + e.getMessage(), e);
        }
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

        // Schedule the next callback if there is none currently scheduled
        if (!isCallbackScheduled)
        {
        	scheduleCallback();
        }
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " spec=" + scheduleSpec;
    }

    private void scheduleCallback()
    {
    	isCallbackScheduled = true;
        long current = context.getSchedulingService().getTime();

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".scheduleCallback Scheduled new callback for " +
                    " now=" + current +
                    " currentReferencePoint=" + currentReferencePoint +
                    " spec=" + scheduleSpec);
        }

        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                OutputConditionCrontab.this.isCallbackScheduled = false;
                OutputConditionCrontab.this.outputCallback.continueOutputProcessing(DO_OUTPUT, FORCE_UPDATE);
                scheduleCallback();
            }
        };
        handle = new EPStatementHandleCallback(context.getEpStatementHandle(), callback);
        context.getSchedulingService().add(scheduleSpec, handle, scheduleSlot);
    }

    private static final Log log = LogFactory.getLog(OutputConditionTime.class);


}
