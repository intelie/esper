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
import net.esper.eql.parse.CronParameter;

/**
 * Factory for 'crontab' observers that indicate truth when a time point was reached.
 */
public class TimerAtObserverFactory implements ObserverFactory
{
    private ScheduleSpec spec = null;

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

        spec = computeValues(observerParameters.toArray());
    }

    private static SortedSet<Integer> computeValues(Object unitParameter, ScheduleUnit unit)
    {
        if (unitParameter instanceof Integer)
        {
            SortedSet<Integer> result = new TreeSet<Integer>();
            result.add((Integer) unitParameter);
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

    private static ScheduleSpec computeValues(Object[] args)
    {
        EnumMap<ScheduleUnit, SortedSet<Integer>> unitMap = new EnumMap<ScheduleUnit, SortedSet<Integer>>(ScheduleUnit.class);
        Object minutes = args[0];
        Object hours = args[1];
        Object daysOfMonth = args[2];
        Object months = args[3];
        Object daysOfWeek = args[4];
        unitMap.put(ScheduleUnit.MINUTES, computeValues(minutes, ScheduleUnit.MINUTES));
        unitMap.put(ScheduleUnit.HOURS, computeValues(hours, ScheduleUnit.HOURS));
        SortedSet<Integer> resultMonths = computeValues(months, ScheduleUnit.MONTHS);
        if (daysOfWeek instanceof CronParameter && daysOfMonth instanceof CronParameter)
        {
            throw
                    new IllegalArgumentException("Invalid combination between days of week and days of month fields for timer:at");
        }
        if (resultMonths != null && resultMonths.size() == 1 && (resultMonths.first() instanceof Integer))
        {
            // If other arguments are cronParameters, use it for later computations
            CronParameter parameter = null;
            if (daysOfMonth instanceof CronParameter)
            {
                parameter = ((CronParameter) daysOfMonth);
            }
            else if (daysOfWeek instanceof CronParameter)
            {
                parameter = ((CronParameter) daysOfWeek);
            }
            if (parameter != null)
            {
                parameter.setMonth(resultMonths.first());
            }
        }
        SortedSet<Integer> resultDaysOfWeek = computeValues(daysOfWeek, ScheduleUnit.DAYS_OF_WEEK);
        SortedSet<Integer> resultDaysOfMonth = computeValues(daysOfMonth, ScheduleUnit.DAYS_OF_MONTH);
        if (resultDaysOfWeek != null && resultDaysOfWeek.size() == 1 && (resultDaysOfWeek.first() instanceof Integer))
        {
            // The result is in the form "last xx of the month
            // Days of week is replaced by a wildcard and days of month is updated with
            // the computation of "last xx day of month".
            // In this case "days of month" parameter has to be a wildcard.
            if (resultDaysOfWeek.first() > 6)
            {
                if (resultDaysOfMonth != null)
                {
                    throw
                            new IllegalArgumentException("Invalid combination between days of week and days of month fields for timer:at");
                }
                resultDaysOfMonth = resultDaysOfWeek;
                resultDaysOfWeek = null;
            }
        }
        if (resultDaysOfMonth != null && resultDaysOfMonth.size() == 1 && (resultDaysOfMonth.first() instanceof Integer))
        {
            if (resultDaysOfWeek != null)
            {
                throw
                        new IllegalArgumentException("Invalid combination between days of week and days of month fields for timer:at");
            }
        }
        unitMap.put(ScheduleUnit.DAYS_OF_WEEK, resultDaysOfWeek);
        unitMap.put(ScheduleUnit.DAYS_OF_MONTH, resultDaysOfMonth);
        unitMap.put(ScheduleUnit.MONTHS, resultMonths);
        if (args.length > 5)
        {
            unitMap.put(ScheduleUnit.SECONDS, computeValues(args[5], ScheduleUnit.SECONDS));
        }
        return new ScheduleSpec(unitMap);
    }

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator,
                                      Object stateNodeId, Object observerState)
    {
        return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
    }

    private static final Log log = LogFactory.getLog(TimerAtObserverFactory.class);
}
