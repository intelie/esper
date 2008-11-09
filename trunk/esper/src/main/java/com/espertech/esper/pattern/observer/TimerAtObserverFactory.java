/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.type.ScheduleUnit;
import com.espertech.esper.type.CronParameter;
import com.espertech.esper.type.NumberSetParameter;
import com.espertech.esper.schedule.ScheduleSpec;
import com.espertech.esper.schedule.ScheduleSpecUtil;
import com.espertech.esper.schedule.ScheduleParameterException;
import com.espertech.esper.util.MetaDefItem;

/**
 * Factory for 'crontab' observers that indicate truth when a time point was reached.
 */
public class TimerAtObserverFactory implements ObserverFactory, MetaDefItem
{
    /**
     * The schedule specification for the timer-at.
     */
    protected ScheduleSpec spec = null;

    public void setObserverParameters(List<Object> observerParameters) throws ObserverParameterException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".setObserverParameters " + observerParameters);
        }

        if ((observerParameters.size() < 5) || (observerParameters.size() > 6))
        {
            throw new ObserverParameterException("Invalid number of parameters for timer:at");
        }

        try
        {
            spec = ScheduleSpecUtil.computeValues(observerParameters.toArray());
        }
        catch (ScheduleParameterException e)
        {
            throw new ObserverParameterException("Error computing observer schedule specification: " + e.getMessage(), e);
        }
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator,
                                      Object stateNodeId, Object observerState)
    {
        return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
    }

    private static final Log log = LogFactory.getLog(TimerAtObserverFactory.class);
}
