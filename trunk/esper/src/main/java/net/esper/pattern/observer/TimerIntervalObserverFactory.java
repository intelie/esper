/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.observer;

import net.esper.eql.parse.TimePeriodParameter;
import net.esper.pattern.MatchedEventMap;
import net.esper.pattern.PatternContext;
import net.esper.pattern.guard.GuardParameterException;
import net.esper.schedule.ScheduleSpec;
import net.esper.util.MetaDefItem;
import net.esper.util.JavaClassHelper;

import java.util.List;

/**
 * Factory for making observer instances.
 */
public class TimerIntervalObserverFactory implements ObserverFactory, MetaDefItem
{
    /**
     * Number of milliseconds after which the interval should fire.
     */
    protected long milliseconds;

    public void setObserverParameters(List<Object> observerParameters) throws ObserverParameterException
    {
        String errorMessage = "Timer-interval observer requires a single numeric or time period parameter";
        if (observerParameters.size() != 1)
        {
            throw new ObserverParameterException(errorMessage);
        }

        Object parameter = observerParameters.get(0);
        if (parameter instanceof TimePeriodParameter)
        {
            TimePeriodParameter param = (TimePeriodParameter) parameter;
            milliseconds = Math.round(1000d * param.getNumSeconds());
        }
        else if (!(parameter instanceof Number))
        {
            throw new ObserverParameterException(errorMessage);
        }
        else
        {
            Number param = (Number) parameter;
            if (JavaClassHelper.isFloatingPointNumber(param))
            {
                milliseconds = Math.round(1000d * param.doubleValue());
            }
            else
            {
                milliseconds = 1000 * param.longValue();
            }
        }
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId, Object observerState)
    {
        return new TimerIntervalObserver(milliseconds, context, beginState, observerEventEvaluator);
    }
}