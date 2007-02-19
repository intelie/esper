package net.esper.pattern.observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.pattern.PatternContext;
import net.esper.pattern.MatchedEventMap;
import net.esper.schedule.ScheduleUnit;
import net.esper.schedule.ScheduleSpec;
import net.esper.eql.parse.NumberSetParameter;

/**
 * Factory for 'crontab' observers that indicate truth when a time point was reached.
 */
public class TimerAtObserverFactory implements ObserverFactory
{
    private ScheduleSpec spec = null;

    /**
     * Ctor.
     * The crontab observer requires a schedule specification that is extracted from arguments.
     * @param args - schedule specification
     */
    public TimerAtObserverFactory(Object[] args)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".TimerAtObserverFactory " + Arrays.toString(args));
        }

        if ((args.length < 5) || (args.length > 6))
        {
            throw new IllegalArgumentException("Invalid number of parameters for timer:at");
        }

        EnumMap<ScheduleUnit, SortedSet<Integer>> unitMap = new EnumMap<ScheduleUnit, SortedSet<Integer>>(ScheduleUnit.class);
        unitMap.put(ScheduleUnit.MINUTES, computeValues(args[0], ScheduleUnit.MINUTES));
        unitMap.put(ScheduleUnit.HOURS, computeValues(args[1], ScheduleUnit.HOURS));
        unitMap.put(ScheduleUnit.DAYS_OF_WEEK, computeValues(args[2], ScheduleUnit.DAYS_OF_WEEK));
        unitMap.put(ScheduleUnit.DAYS_OF_MONTH, computeValues(args[3], ScheduleUnit.DAYS_OF_MONTH));
        unitMap.put(ScheduleUnit.MONTHS, computeValues(args[4], ScheduleUnit.MONTHS));
        if (args.length > 5)
        {
            unitMap.put(ScheduleUnit.SECONDS, computeValues(args[5], ScheduleUnit.SECONDS));
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

    public EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator)
    {
        return new TimerAtObserver(spec, context, beginState, observerEventEvaluator);
    }

    private static final Log log = LogFactory.getLog(TimerAtObserverFactory.class);
}
