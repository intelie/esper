/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleUnit;
import net.esper.schedule.ScheduleSpec;
import net.esper.eql.parse.NumberSetParameter;
import net.esper.util.MetaDefItem;

/**
 * Factory for 'crontab' observers that indicate truth when a time point was reached.
 */
public class TimerAtObserverFactory implements ObserverFactory, MetaDefItem
{
    /**
     * The specification of the crontab schedule.
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

        EnumMap<ScheduleUnit, SortedSet<Integer>> unitMap = new EnumMap<ScheduleUnit, SortedSet<Integer>>(ScheduleUnit.class);
        unitMap.put(ScheduleUnit.MINUTES, computeValues(observerParameters.get(0), ScheduleUnit.MINUTES));
        unitMap.put(ScheduleUnit.HOURS, computeValues(observerParameters.get(1), ScheduleUnit.HOURS));
        unitMap.put(ScheduleUnit.DAYS_OF_WEEK, computeValues(observerParameters.get(2), ScheduleUnit.DAYS_OF_WEEK));
        unitMap.put(ScheduleUnit.DAYS_OF_MONTH, computeValues(observerParameters.get(3), ScheduleUnit.DAYS_OF_MONTH));
        unitMap.put(ScheduleUnit.MONTHS, computeValues(observerParameters.get(4), ScheduleUnit.MONTHS));
        if (observerParameters.size() > 5)
        {
            unitMap.put(ScheduleUnit.SECONDS, computeValues(observerParameters.get(5), ScheduleUnit.SECONDS));
        }
        spec = new ScheduleSpec(unitMap);
    }

    private static SortedSet<Integer> computeValues(Object unitParameter, ScheduleUnit unit)
    {
        if (unitParameter instanceof Integer)
        {
            SortedSet<Integer> result = new TreeSet<Integer>();
            result.add((Integer)unitParameter);
            return result;
        }

        NumberSetParameter numberSet = (NumberSetParameter) unitParameter;
        if (numberSet.isWildcard(unit.min(), unit.min()))
        {
            return null;
        }

        Set<Integer> result = numberSet.getValuesInRange(unit.min(), unit.max());
        SortedSet<Integer> resultSorted = new TreeSet<Integer>();
        resultSorted.addAll(result);

        return resultSorted;
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator,
                                      Object stateNodeId, Object observerState)
    {
        return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
    }

    private static final Log log = LogFactory.getLog(TimerAtObserverFactory.class);
}
